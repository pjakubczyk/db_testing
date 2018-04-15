package org.jakubczyk.dbtesting.domain.interactor

import org.jakubczyk.dbtesting.MySpecification
import org.jakubczyk.dbtesting.domain.repository.datasource.TodoDatasource
import rx.Completable
import rx.Observable
import rx.observers.TestSubscriber

class StoreTodoItemUseCaseSpec extends MySpecification {

    def todoDataSource = Mock(TodoDatasource)

    StoreTodoItemUseCase useCase

    def "setup"() {
        useCase = new StoreTodoItemUseCase(
                testMainScheduler,
                testIoScheduler,
                todoDataSource
        )
    }

    def "should store new value in datasource"() {
        given:
        def testSubscriber = new TestSubscriber<Boolean>()

        todoDataSource.addNewEntity("new_value") >> Observable.just(true)

        when:
        useCase.execute(testSubscriber, "new_value")

        and:
        triggerScheduler()

        then:
        testSubscriber.onNextEvents[0]
    }

}
