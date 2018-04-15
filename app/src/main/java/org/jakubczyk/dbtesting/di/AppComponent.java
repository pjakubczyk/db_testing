package org.jakubczyk.dbtesting.di;

import org.jakubczyk.dbtesting.common.IoScheduler;
import org.jakubczyk.dbtesting.common.MainScheduler;
import org.jakubczyk.dbtesting.db.RequeryDatastoreModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ThreadingModule.class, RequeryDatastoreModule.class})
public interface AppComponent {

    MainScheduler getMainScheduler();

    IoScheduler getIoScheduler();
}
