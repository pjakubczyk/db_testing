package org.jakubczyk.dbtesting.screens;

import org.jakubczyk.dbtesting.data.entity.TodoEntity;

import java.util.List;

public interface TodoContract {

    interface View {

        void showEntities(List<TodoEntity> todoEntities);
    }

    interface Presenter {

        void create(View view);

        void destroy();

        void addNewItem(String value);
    }
}
