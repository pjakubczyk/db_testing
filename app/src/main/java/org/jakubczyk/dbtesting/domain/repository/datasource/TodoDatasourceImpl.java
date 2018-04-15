package org.jakubczyk.dbtesting.domain.repository.datasource;

import org.jakubczyk.dbtesting.data.entity.TodoEntity;
import org.jakubczyk.dbtesting.db.RequeryDatastore;
import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

public class TodoDatasourceImpl implements TodoDatasource {


    private final SingleEntityStore<Persistable> dataStore;

    @Inject
    public TodoDatasourceImpl(
            RequeryDatastore requeryDatastore) {
        dataStore = requeryDatastore.getDataStore();
    }

    @Override
    public Observable<List<TodoEntity>> getTodoEntitiesStream() {
        return Observable.just(Collections.<TodoEntity>emptyList());
    }

    @Override
    public Observable<Boolean> addNewEntity(String newEntityValue) {
        TodoDbEntityEntity todoDbEntityEntity = new TodoDbEntityEntity();
        todoDbEntityEntity.setItemValue(newEntityValue);

        return dataStore.insert(todoDbEntityEntity).flatMap(new Func1<TodoDbEntityEntity, Single<Boolean>>() {
            @Override
            public Single<Boolean> call(TodoDbEntityEntity todoDbEntityEntity) {
                return Single.just(true);
            }
        }).toObservable();
    }
}
