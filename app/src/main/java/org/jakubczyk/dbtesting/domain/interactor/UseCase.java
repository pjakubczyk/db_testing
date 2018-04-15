package org.jakubczyk.dbtesting.domain.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jakubczyk.dbtesting.common.IoScheduler;
import org.jakubczyk.dbtesting.common.MainScheduler;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public abstract class UseCase<OutputType, InputParams> {

    @NonNull
    private final MainScheduler mainScheduler;
    @NonNull
    private final IoScheduler ioScheduler;

    @Nullable
    private Subscription subscription;
    private Observable<OutputType> observable;

    UseCase(@NonNull final MainScheduler mainScheduler, @NonNull final IoScheduler ioScheduler) {
        this.mainScheduler = mainScheduler;
        this.ioScheduler = ioScheduler;
    }

    abstract Observable<OutputType> buildUseCaseObservable(@NonNull final InputParams params);

    public void execute(@NonNull final Observer<OutputType> observer, @NonNull final InputParams params) {
        if (observable != null) {
            unsubscribe();
        }

        observable = buildUseCaseObservable(params);

        subscription = observable
                .subscribeOn(ioScheduler.get())
                .observeOn(mainScheduler.get())
                .subscribe(observer);
    }

    public void unsubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
                subscription = null;
            }
        }
    }
}
