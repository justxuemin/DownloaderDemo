package com.coocoo.downloaderdemo.downloader.internal;

import java.util.Locale;


public abstract class NamedRunnable implements Runnable {
    protected final String name;

    public NamedRunnable(String format, Object... args) {
        this.name = format(format, args);
    }

    @Override public final void run() {
        String oldName = Thread.currentThread().getName();
        Thread.currentThread().setName(name);
        try {
            execute();
        } finally {
            Thread.currentThread().setName(oldName);
        }
    }

    protected abstract void execute();

    public static String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }
}
