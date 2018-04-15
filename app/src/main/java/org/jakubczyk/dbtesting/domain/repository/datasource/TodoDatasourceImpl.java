package org.jakubczyk.dbtesting.domain.repository.datasource;

import org.jakubczyk.dbtesting.data.entity.TodoEntity;
import org.jakubczyk.dbtesting.db.RequeryDatastore;

import java.util.List;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import rx.Completable;
import rx.Observable;

public class TodoDatasourceImpl implements TodoDatasource {


    private final SingleEntityStore<Persistable> dataStore;

    @Inject
    public TodoDatasourceImpl(
            RequeryDatastore requeryDatastore) {
        dataStore = requeryDatastore.getDataStore();
    }

    @Override
    public Observable<List<TodoEntity>> getTodoEntitiesStream() {
        return null;
    }

    @Override
    public Completable addNewEntity(String newEntityValue) {
        return null;
    }
}
