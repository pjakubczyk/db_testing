package org.jakubczyk.dbtesting.domain.interactor;

import android.support.annotation.NonNull;

import org.jakubczyk.dbtesting.common.IoScheduler;
import org.jakubczyk.dbtesting.common.MainScheduler;
import org.jakubczyk.dbtesting.domain.repository.datasource.TodoDatasource;

import javax.inject.Inject;

import rx.Observable;

public class StoreTodoItemUseCase extends UseCase<Boolean, String> {

    private final TodoDatasource todoDatasource;

    @Inject
    public StoreTodoItemUseCase(
            @NonNull MainScheduler mainScheduler,
            @NonNull IoScheduler ioScheduler,
            TodoDatasource todoDatasource) {
        super(mainScheduler, ioScheduler);
        this.todoDatasource = todoDatasource;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(@NonNull String newValue) {
        return todoDatasource
                .addNewEntity(newValue);
    }
}
