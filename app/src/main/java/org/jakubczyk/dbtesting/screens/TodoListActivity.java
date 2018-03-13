package org.jakubczyk.dbtesting.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.jakubczyk.dbtesting.BaseActivity;
import org.jakubczyk.dbtesting.R;
import org.jakubczyk.dbtesting.screens.di.DaggerTodoListComponent;
import org.jakubczyk.dbtesting.screens.di.TodoListComponent;


public class TodoListActivity extends BaseActivity implements TodoContract.View {

    private TodoListComponent component;
    private TodoContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        component = DaggerTodoListComponent.builder().appComponent(appComponent).build();
        presenter = component.getPresenter();

        presenter.create(this);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
