package com.ibotn.ibotncamera2;

import android.app.Application;

public class IbotnCamera2App extends Application {
    private static IbotnCamera2App app;
    public static IbotnCamera2App getInstance()
    {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
