package org.jakubczyk.dbtesting.screens

import spock.lang.Specification

class TodoListPresenterSpec extends Specification {

    def view = Mock(TodoContract.View)

    TodoListPresenter presenter

    def "setup"() {
        presenter = new TodoListPresenter()
    }

    def "should release view reference on destroy"() {
        given:
        presenter.create(view)

        when:
        presenter.destroy()

        then:
        presenter.view == null
    }
}
