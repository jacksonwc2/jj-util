package com.jj.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ThreadUtil {

    private ThreadUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static List<Thread> getAllThreads() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);

        return Arrays.asList(threadArray);
    }

    public static List<Thread> getThreadByName(String threadName) {

        List<Thread> allThreads = getAllThreads();
        return allThreads.stream().filter(w -> w.getName().equals(threadName)).collect(Collectors.toList());

    }

    public static Boolean interruptThreadByName(String threadName) {
        List<Thread> threads = getThreadByName(threadName);
        if (threads != null && !threads.isEmpty()) {
            for (Thread thread : threads) {
                thread.interrupt();
            }

            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

}
