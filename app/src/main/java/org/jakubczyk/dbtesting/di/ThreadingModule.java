package org.jakubczyk.dbtesting.di;

import org.jakubczyk.dbtesting.common.IoScheduler;
import org.jakubczyk.dbtesting.common.MainScheduler;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ThreadingModule {

    @Provides
    MainScheduler provideMainScheduler() {
        return new MainScheduler(AndroidSchedulers.mainThread());
    }

    @Provides
    IoScheduler provideIoScheduler() {
        return new IoScheduler(Schedulers.from(Executors.newFixedThreadPool(6)));
    }
}
