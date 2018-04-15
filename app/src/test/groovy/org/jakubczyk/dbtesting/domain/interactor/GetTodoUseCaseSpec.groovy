package org.jakubczyk.dbtesting.domain.interactor

import org.jakubczyk.dbtesting.MySpecification
import org.jakubczyk.dbtesting.data.entity.TodoEntity
import rx.observers.TestSubscriber

class GetTodoUseCaseSpec extends MySpecification {

    GetTodoUseCase getTodoUseCase

    def "setup"() {
        getTodoUseCase = new GetTodoUseCase(
                testMainScheduler,
                testIoScheduler
        )
    }

    def "should load 20 items"() {
        given:
        def testSubscriber = new TestSubscriber<List<TodoEntity>>()

        when:
        getTodoUseCase.execute(testSubscriber, new Object())

        and:
        triggerScheduler()

        then:
        testSubscriber.onNextEvents[0].size() == 20
    }
}
