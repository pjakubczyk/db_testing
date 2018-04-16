package org.jakubczyk.dbtesting.data.mapper;

import org.jakubczyk.dbtesting.data.entity.TodoEntity;
import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity;

import java.util.Date;

public class TodoItemMapper {

    private TodoDbEntityEntity todoDbEntityEntity;

    public TodoItemMapper(TodoDbEntityEntity todoDbEntityEntity) {
        this.todoDbEntityEntity = todoDbEntityEntity;
    }

    public TodoEntity toTodoEntity() {
        return new TodoEntity(todoDbEntityEntity.getId(), todoDbEntityEntity.getItemValue(), new Date());
    }
}
