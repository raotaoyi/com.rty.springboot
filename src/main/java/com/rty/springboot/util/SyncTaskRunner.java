package com.rty.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
        GROUPS.put(groupId, new Group());
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
        private boolean sync = true;
        private boolean isRunning = false;
        private long startTime = 0;
        private ExecutorService service = Executors.newFixedThreadPool(15);
        private List<Proxy> waiting = Collections.synchronizedList(new ArrayList<>());
        private List<Proxy> running = Collections.synchronizedList(new ArrayList<>());
        private List<Future> results = Collections.synchronizedList(new ArrayList<>());
        private CountDownLatch count;

        private void start() {
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
        }
    }

    private static class Proxy<V> implements Callable<V> {
        private String taskName;
        private Callable runnable;
        private ResultProcess resultProcess;
        private Group group;

        public Proxy(String taskName, Callable runnable) {
            this(taskName, runnable, null, 0);
        }

        public Proxy(String taskName, Callable runnable, ResultProcess resultProcess, int groupId) {
            this.taskName = taskName;
            this.runnable = runnable;
            this.resultProcess = resultProcess;
            this.group = GROUPS.get(groupId);
        }

        @Override
        public V call() throws Exception {
            return null;
        }
    }

    private static class ResultProcess {

    }
}
