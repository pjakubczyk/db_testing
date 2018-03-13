package org.jakubczyk.dbtesting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.jakubczyk.dbtesting.di.AppComponent;

public class BaseActivity extends AppCompatActivity {

    AppComponent appComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication application = (MyApplication) getApplication();
        appComponent = application.getAppComponent();
    }
}
