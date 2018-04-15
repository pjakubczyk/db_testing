package org.jakubczyk.dbtesting.domain.interactor;

import android.support.annotation.NonNull;

import org.jakubczyk.dbtesting.common.IoScheduler;
import org.jakubczyk.dbtesting.common.MainScheduler;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

public class StoreTodoItemUseCase extends UseCase<Boolean, String> {

    @Inject
    public StoreTodoItemUseCase(
            @NonNull MainScheduler mainScheduler,
            @NonNull IoScheduler ioScheduler) {
        super(mainScheduler, ioScheduler);
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(@NonNull String s) {
        return Observable.just(true).delay(4, TimeUnit.SECONDS);
    }
}
