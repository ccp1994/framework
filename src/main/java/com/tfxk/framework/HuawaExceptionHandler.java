package com.tfxk.framework;


import java.lang.Thread.UncaughtExceptionHandler;


public class HuawaExceptionHandler implements UncaughtExceptionHandler {


    private UncaughtExceptionHandler defaultUEH;


    public HuawaExceptionHandler() {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread t, Throwable e) {
//        final Writer result = new StringWriter();
//        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace();

        defaultUEH.uncaughtException(t, e);
    }

}