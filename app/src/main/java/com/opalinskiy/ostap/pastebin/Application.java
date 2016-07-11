package com.opalinskiy.ostap.pastebin;


import android.content.Context;


public class Application extends android.app.Application {
    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
