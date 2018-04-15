package org.jakubczyk.dbtesting.domain.repository.datasource;

import org.jakubczyk.dbtesting.data.entity.TodoEntity;

import java.util.List;

import rx.Observable;

public interface TodoDatasource {

    Observable<List<TodoEntity>> getTodoEntitiesStream();

    Observable<Boolean> addNewEntity(String newEntityValue);
}
