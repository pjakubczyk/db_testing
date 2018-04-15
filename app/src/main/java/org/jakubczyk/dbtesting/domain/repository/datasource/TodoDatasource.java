package org.jakubczyk.dbtesting.domain.repository.datasource;

import org.jakubczyk.dbtesting.data.entity.TodoEntity;

import java.util.List;

import rx.Completable;
import rx.Observable;

public interface TodoDatasource {

    Observable<List<TodoEntity>> getTodoEntitiesStream();

    Completable addNewEntity(String newEntityValue);
}
