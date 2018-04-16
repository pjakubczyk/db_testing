package org.jakubczyk.dbtesting.domain.interactor;

import android.support.annotation.NonNull;

import org.jakubczyk.dbtesting.common.IoScheduler;
import org.jakubczyk.dbtesting.common.MainScheduler;
import org.jakubczyk.dbtesting.data.entity.TodoEntity;
import org.jakubczyk.dbtesting.data.mapper.TodoItemMapper;
import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity;
import org.jakubczyk.dbtesting.domain.repository.datasource.TodoDatasource;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class GetTodoUseCase extends UseCase<List<TodoEntity>, Object> {

    private final TodoDatasource todoDatasource;

    @Inject
    GetTodoUseCase(
            @NonNull MainScheduler mainScheduler,
            @NonNull IoScheduler ioScheduler,
            TodoDatasource todoDatasource
    ) {
        super(mainScheduler, ioScheduler);
        this.todoDatasource = todoDatasource;
    }

    @Override
    Observable<List<TodoEntity>> buildUseCaseObservable(@NonNull Object o) {
        return todoDatasource
                .getTodoEntitiesStream()
                .flatMap((Func1<List<TodoDbEntityEntity>, Observable<TodoDbEntityEntity>>) Observable::from)
                .map(todoDbEntityEntity -> new TodoItemMapper(todoDbEntityEntity).toTodoEntity())
                .toList();
    }
}
