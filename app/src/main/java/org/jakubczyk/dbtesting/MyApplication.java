package org.jakubczyk.dbtesting;

import android.app.Application;

import org.jakubczyk.dbtesting.di.AppComponent;
import org.jakubczyk.dbtesting.di.DaggerAppComponent;


public class MyApplication extends Application {


    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return applicationComponent;
    }
}
