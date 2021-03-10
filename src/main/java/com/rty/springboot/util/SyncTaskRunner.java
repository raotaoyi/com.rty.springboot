package com.rty.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class SyncTaskRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(SyncTaskRunner.class);

    private static Map<Integer, Group> GROUPS = new ConcurrentHashMap<>();

    private static int autoGroupId = 100;

    static {
        //默认创建
        createGroup(0);
    }

    public static int createGroup() {
        int groupId = 0;
        do {
            groupId = autoGroupId++;
        } while (GROUPS.containsKey(groupId));
        createGroup(groupId);
        return groupId;
    }

    public static void createGroup(int groupId) {
        if (GROUPS.containsKey(groupId)) {
            throw new RuntimeException("group is exist");
        }
        GROUPS.put(groupId, new Group());
    }

    private static class Group {

    }

    private static class Proxy<V> implements Callable<V> {

        @Override
        public V call() throws Exception {
            return null;
        }
    }

    private static class ResultProcess {

    }
}
