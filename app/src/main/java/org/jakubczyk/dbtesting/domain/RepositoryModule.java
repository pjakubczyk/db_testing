package org.jakubczyk.dbtesting.domain;

import org.jakubczyk.dbtesting.domain.repository.datasource.RequeryTodoDatasource;
import org.jakubczyk.dbtesting.domain.repository.datasource.TodoDatasource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract TodoDatasource provideTodoDatasource(RequeryTodoDatasource todoDatasource);
}
