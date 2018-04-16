package org.jakubczyk.dbtesting.domain.repository.datasource;

import org.jakubczyk.dbtesting.db.RequeryDatastore;
import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

public class TodoDatasourceImpl implements TodoDatasource {


    private SingleEntityStore<Persistable> dataStore;

    BehaviorSubject<List<TodoDbEntityEntity>> behaviorSubject = BehaviorSubject.create();

    @Inject
    public TodoDatasourceImpl(
            RequeryDatastore requeryDatastore) {
        dataStore = requeryDatastore.getDataStore();
    }

    @Override
    public Observable<List<TodoDbEntityEntity>> getTodoEntitiesStream() {
        // empty stream
        if (behaviorSubject.getValue() == null) {
            dataStore
                    .select(TodoDbEntityEntity.class)
                    .get()
                    .toObservable()
                    .toList()
                    .subscribe(behaviorSubject::onNext);
        }

        return behaviorSubject;
    }

    @Override
    public Observable<Boolean> addNewEntity(String newEntityValue) {
        TodoDbEntityEntity todoDbEntityEntity = new TodoDbEntityEntity();
        todoDbEntityEntity.setItemValue(newEntityValue);
        todoDbEntityEntity.setCreatedAt(new Date());

        return dataStore
                .insert(todoDbEntityEntity)
                .flatMap((Func1<TodoDbEntityEntity, Single<Boolean>>) todoDbEntityEntity1 -> Single.just(true))
                .toObservable()
                .doOnNext(aBoolean -> dataStore
                        .select(TodoDbEntityEntity.class)
                        .get()
                        .toObservable()
                        .toList()
                        .subscribe(behaviorSubject::onNext)
                );
    }
}
