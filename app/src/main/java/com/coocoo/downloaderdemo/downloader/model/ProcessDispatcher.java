package com.coocoo.downloaderdemo.downloader.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProcessDispatcher {

    private int maxProcess = 64;

    /** Executes calls. Created lazily. */
    private ExecutorService executorService;

    /** Ready async calls in the order they'll be run. */
    private final Deque<ProcessCall.ProcessTask> readyAsyncCalls = new ArrayDeque<>();

    /** Running asynchronous calls. Includes canceled calls that haven't finished yet. */
    private final Deque<ProcessCall.ProcessTask> runningAsyncCalls = new ArrayDeque<>();

    public ProcessDispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProcessDispatcher() {
    }

    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), threadFactory("Process Dispatcher", false));
        }
        return executorService;
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

    synchronized void enqueue(ProcessCall.ProcessTask task) {
        if (runningAsyncCalls.size() < maxProcess) {
            runningAsyncCalls.add(task);
            executorService().execute(task);
        } else {
            readyAsyncCalls.add(task);
        }
    }

    private void promoteCalls() {
        if (runningAsyncCalls.size() >= maxProcess) return; // Already running max capacity.
        if (readyAsyncCalls.isEmpty()) return; // No ready calls to promote.

        for (Iterator<ProcessCall.ProcessTask> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            ProcessCall.ProcessTask call = i.next();
            i.remove();
            runningAsyncCalls.add(call);
            executorService().execute(call);
            if (runningAsyncCalls.size() >= maxProcess) return; // Reached max capacity.
        }
    }

    private <T> void finished(Deque<T> calls, T call, boolean promoteCalls) {
        synchronized (this) {
            if (!calls.remove(call)) throw new AssertionError("Call wasn't in-flight!");
            if (promoteCalls) promoteCalls();
        }
    }

    void finished(ProcessCall.ProcessTask call) {
        finished(runningAsyncCalls, call, true);
    }

}
