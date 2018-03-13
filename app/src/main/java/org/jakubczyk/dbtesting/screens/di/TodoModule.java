package org.jakubczyk.dbtesting.screens.di;

import org.jakubczyk.dbtesting.di.ForActivity;
import org.jakubczyk.dbtesting.screens.TodoContract;
import org.jakubczyk.dbtesting.screens.TodoListPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TodoModule {

    @Binds
    @ForActivity
    abstract TodoContract.Presenter bindPresenter(TodoListPresenter todoListPresenter);
}
