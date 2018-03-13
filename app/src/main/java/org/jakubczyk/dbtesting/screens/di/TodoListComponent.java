package org.jakubczyk.dbtesting.screens.di;

import org.jakubczyk.dbtesting.di.AppComponent;
import org.jakubczyk.dbtesting.di.ForActivity;
import org.jakubczyk.dbtesting.screens.TodoContract;

import dagger.Component;

@ForActivity
@Component(modules = TodoModule.class, dependencies = AppComponent.class)
public interface TodoListComponent {

    TodoContract.Presenter getPresenter();
}
