package org.jakubczyk.dbtesting.domain.repository.datasource;

import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity;

import java.util.List;

import rx.Observable;

public interface TodoDatasource {

    Observable<List<TodoDbEntityEntity>> getTodoEntitiesStream();

    Observable<Boolean> addNewEntity(String newEntityValue);
}
