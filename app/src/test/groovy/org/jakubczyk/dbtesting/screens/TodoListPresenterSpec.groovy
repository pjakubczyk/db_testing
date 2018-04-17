package org.jakubczyk.dbtesting.screens

import org.jakubczyk.dbtesting.data.entity.TodoEntity
import org.jakubczyk.dbtesting.domain.interactor.GetTodoUseCase
import org.jakubczyk.dbtesting.domain.interactor.StoreTodoItemUseCase
import spock.lang.Specification

class TodoListPresenterSpec extends Specification {

    def view = Mock(TodoContract.View)
    def getTodoUseCase = Mock(GetTodoUseCase)
    def storeTodoItemUseCase = Mock(StoreTodoItemUseCase)

    TodoListPresenter presenter
    TodoListPresenter.TodoListObserver todoListObserver

    def "setup"() {
        presenter = new TodoListPresenter(getTodoUseCase, storeTodoItemUseCase)
        todoListObserver = new TodoListPresenter.TodoListObserver(presenter)
    }

    def "should release view reference on destroy"() {
        given:
        presenter.create(view)

        when:
        presenter.destroy()

        then:
        presenter.view == null
    }

    def "should unsubscribe use case on destroy"() {
        given:
        presenter.create(view)

        when:
        presenter.destroy()

        then:
        1 * getTodoUseCase.unsubscribe()
    }

    def "should inform UI on new data"() {
        given:
        presenter.create(view)

        and:
        def entities = [new TodoEntity(1, "value", new Date())]

        when:
        todoListObserver.onNext(entities)

        then:
        1 * view.showEntities(entities)
    }

    def "should store new value"() {
        given:
        presenter.create(view)

        when:
        presenter.addNewItem("new_value")

        then:
        1 * storeTodoItemUseCase.execute(_, "new_value")
    }
}
