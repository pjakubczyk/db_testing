package org.jakubczyk.dbtesting.screens;

public interface TodoContract {

    interface View {

    }

    interface Presenter {

        void create(View view);

        void destroy();
    }
}
