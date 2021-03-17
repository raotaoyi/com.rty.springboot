package com.rty.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import java.util.*;
import java.util.concurrent.*;

/**
 * 多线程，以及异常重试
 */
public class SyncTaskRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(SyncTaskRunner.class);

    private static Map<Integer, Group> GROUPS = new ConcurrentHashMap<>();

    private static int autoGroupId = 100;

    static {
        //默认创建
        createGroup(0);
    }

    public synchronized static int createGroup() {
        int groupId = 0;
        do {
            groupId = autoGroupId++;
        } while (GROUPS.containsKey(groupId));
        createGroup(groupId);
        return groupId;
    }

    public synchronized static void createGroup(int groupId) {
        if (GROUPS.containsKey(groupId)) {
            throw new RuntimeException("group is exist");
        }
        GROUPS.put(groupId, new Group(groupId));
    }

    public synchronized static void add(String taskName, Runnable runnable) {
        add(taskName, runnable, 0);
    }

    public synchronized static void add(String taskName, Runnable runnable, int groupId) {
        if (GROUPS.get(groupId).sync) {
            asyncAdd(taskName, () -> {
                runnable.run();
                return null;
            }, null, groupId);
        } else {
            runNow(taskName, () -> {
                runnable.run();
                return null;
            });
        }
    }

    public static void start() {
        start(0);
    }

    public static void start(int groupId) {
        GROUPS.get(groupId).start();
    }

    private synchronized static void asyncAdd(String taskName, Callable runnable, ResultProcess resultProcess, int groupId) {
        if (GROUPS.get(groupId).isRunning) {
            throw new RuntimeException("this group is running the groupid is " + groupId + "the start time is " +
                    GROUPS.get(groupId).startTime);
        }
        synchronized (SyncTaskRunner.class) {
            GROUPS.get(groupId).waiting.add(new Proxy<>(taskName, runnable, resultProcess, groupId));
        }
    }

    private synchronized static void runNow(String taskName, Callable runnable) {
        try {
            new Proxy<>(taskName, runnable).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Group {
        private int groupId;
        private boolean sync = true;
        private boolean isRunning = false;
        private long startTime = 0;
        private ExecutorService service = Executors.newFixedThreadPool(15);
        private List<Proxy> waiting = Collections.synchronizedList(new ArrayList<>());
        private List<Proxy> running = Collections.synchronizedList(new ArrayList<>());
        private List<Future> results = Collections.synchronizedList(new ArrayList<>());
        private List<Map<String, Long>> orderPrintResults = Collections.synchronizedList(new ArrayList<>());
        private CountDownLatch count;

        private final Object group_object = new Object();

        public Group(int groupId) {
            this.groupId = groupId;
        }

        private void start() {
            int total = waiting.size();
            if (waiting.size() == 0) {
                return;
            }
            isRunning = true;
            startTime = System.currentTimeMillis();
            running.addAll(waiting);
            waiting.clear();
            count = new CountDownLatch(running.size());
            running.forEach((proxy) -> {
                results.add(service.submit(proxy));
            });

            try {
                while (!count.await(10, TimeUnit.SECONDS)) {
                    LOGGER.info("this group process " + (total - count.getCount()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = null;
            //打印耗时时间长的task
            if (orderPrintResults.size() > 0) {
                orderPrintTime();
            }
            isRunning = false;
            running.clear();
            results.clear();
            if (groupId != 0) {
                service.shutdown();
                GROUPS.remove(groupId);
            }
            long endTime = System.currentTimeMillis();
            LOGGER.info("this group finish,ths cost time " + (endTime - startTime));
        }

        private void orderPrintTime() {

        }
    }

    private static class Proxy<V> implements Callable<V> {
        private String taskName;
        private Callable callable;
        private ResultProcess resultProcess;
        private Group group;
        private boolean isSuccess;
        private int tryCount = 0;
        private static int MAX_TRY_COUNT = 10;

        public Proxy(String taskName, Callable runnable) {
            this(taskName, runnable, null, 0);
        }

        public Proxy(String taskName, Callable runnable, ResultProcess resultProcess, int groupId) {
            this.taskName = taskName;
            this.callable = runnable;
            this.resultProcess = resultProcess;
            this.group = GROUPS.get(groupId);
        }

        @Override
        public V call() throws Exception {
            long startTime = System.currentTimeMillis();
            if (!StringUtil.isEmpty(taskName)) {
                LOGGER.info("task " + taskName + " is running");
            }
            try {
                return runWithRetry();
            } catch (Throwable t) {
                t.fillInStackTrace();
                isSuccess = false;
                throw t;
            } finally {
                group.running.remove(this);
                synchronized (group.group_object) {
                    if (group.count != null) {
                        group.count.countDown();
                    }
                }
                long endTime = System.currentTimeMillis();
                if (!StringUtil.isEmpty(taskName)) {
                    Map<String,Long> orderTime=new HashMap<>();
                    orderTime.put(taskName,endTime-startTime);
                    group.orderPrintResults.add(orderTime);
                    LOGGER.info("task " + taskName + " is finish,the cost time is " + (endTime - startTime));
                }
            }
        }

        private V runWithRetry() {
            Throwable throwable = null;
            int tryNoLimit = 0;
            while (tryCount < MAX_TRY_COUNT) {
                try {
                    long startTime = System.currentTimeMillis();
                    V result = (V) this.callable.call();
                    if (resultProcess != null) {
                        resultProcess.doResult(result);
                    }
                    long endTime = System.currentTimeMillis();
                    if (StringUtil.isEmpty(taskName)) {
                        Map<String, Long> orderTime = new HashMap<>();
                        orderTime.put(taskName, endTime - startTime);
                    }
                    return result;

                } catch (CannotGetJdbcConnectionException e) {
                    tryNoLimit++;
                    if (tryNoLimit % 20 == 3) {
                        LOGGER.error("jdbc not connect", e.getLocalizedMessage());
                    }
                    try {
                        Thread.sleep(tryNoLimit < 10 ? 1000 : 20000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                } catch (BadSqlGrammarException e) {
                    throw e;
                } catch (Throwable t) {
                    throwable = t;
                    tryCount++;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
            throw new RuntimeException("task is fail", throwable);
        }
    }

    private static interface ResultProcess<V> {
        public void doResult(V result);
    }
}
