package org.jakubczyk.dbtesting.screens;

import org.jakubczyk.dbtesting.data.entity.TodoEntity;
import org.jakubczyk.dbtesting.domain.interactor.GetTodoUseCase;
import org.jakubczyk.dbtesting.domain.interactor.StoreTodoItemUseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class TodoListPresenter implements TodoContract.Presenter {

    TodoContract.View view;
    private GetTodoUseCase getTodoUseCase;
    private StoreTodoItemUseCase storeTodoItemUseCase;

    @Inject
    public TodoListPresenter(
            GetTodoUseCase getTodoUseCase,
            StoreTodoItemUseCase storeTodoItemUseCase) {
        this.getTodoUseCase = getTodoUseCase;
        this.storeTodoItemUseCase = storeTodoItemUseCase;
    }

    @Override
    public void create(TodoContract.View view) {
        this.view = view;

        getTodoUseCase.execute(new TodoListObserver(), new Object());
    }

    @Override
    public void destroy() {
        getTodoUseCase.unsubscribe();
        view = null;
    }

    @Override
    public void addNewItem(String value) {
        storeTodoItemUseCase.execute(new AddNewItemObserver(), value);
    }

    class TodoListObserver extends Subscriber<List<TodoEntity>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<TodoEntity> todoEntities) {
            view.showEntities(todoEntities);
        }
    }

    class AddNewItemObserver extends Subscriber<Boolean> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Boolean aBoolean) {

        }
    }
}
