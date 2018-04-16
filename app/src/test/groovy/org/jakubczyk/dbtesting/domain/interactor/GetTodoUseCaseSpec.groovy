package org.jakubczyk.dbtesting.domain.interactor

import org.jakubczyk.dbtesting.MySpecification
import org.jakubczyk.dbtesting.data.entity.TodoEntity
import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity
import org.jakubczyk.dbtesting.domain.repository.datasource.TodoDatasource
import rx.Observable
import rx.observers.TestSubscriber

class GetTodoUseCaseSpec extends MySpecification {

    def todoDatasource = Mock(TodoDatasource)

    GetTodoUseCase getTodoUseCase

    def "setup"() {
        getTodoUseCase = new GetTodoUseCase(
                testMainScheduler,
                testIoScheduler,
                todoDatasource
        )
    }

    def "should load 20 items"() {
        given:
        def items = (1..20).collect {

            def entity = new TodoDbEntityEntity()
            entity.setItemValue("item" + it)
            entity
        }

        todoDatasource.getTodoEntitiesStream() >> Observable.just(items)

        def testSubscriber = new TestSubscriber<List<TodoEntity>>()

        when:
        getTodoUseCase.execute(testSubscriber, new Object())

        and:
        triggerScheduler()

        then:
        testSubscriber.onNextEvents[0].size() == 20

        and: "check first value"
        testSubscriber.onNextEvents[0][0].getValue() == "item1"
    }
}
