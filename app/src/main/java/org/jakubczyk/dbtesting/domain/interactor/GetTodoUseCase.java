package org.jakubczyk.dbtesting.domain.interactor;

import android.support.annotation.NonNull;

import org.jakubczyk.dbtesting.common.IoScheduler;
import org.jakubczyk.dbtesting.common.MainScheduler;
import org.jakubczyk.dbtesting.data.entity.TodoEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetTodoUseCase extends UseCase<List<TodoEntity>, Object> {

    @Inject
    GetTodoUseCase(@NonNull MainScheduler mainScheduler, @NonNull IoScheduler ioScheduler) {
        super(mainScheduler, ioScheduler);
    }

    @Override
    Observable<List<TodoEntity>> buildUseCaseObservable(@NonNull Object o) {
        List<TodoEntity> todoEntities = new ArrayList<>(20);

        for (int i = 0; i < 20; ++i) {
            TodoEntity todoEntity = new TodoEntity("item " + i, new Date(System.currentTimeMillis() + i));
            todoEntities.add(todoEntity);
        }

        return Observable.just(todoEntities);
    }
}
