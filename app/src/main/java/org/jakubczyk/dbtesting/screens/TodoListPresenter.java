package org.jakubczyk.dbtesting.screens;

import javax.inject.Inject;

public class TodoListPresenter implements TodoContract.Presenter {

    TodoContract.View view;

    @Inject
    public TodoListPresenter() {

    }

    @Override
    public void create(TodoContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }
}
