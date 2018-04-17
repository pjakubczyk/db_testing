package org.jakubczyk.dbtesting.domain.repository.datasource

import io.requery.rx.SingleEntityStore
import org.jakubczyk.dbtesting.MySpecification
import org.jakubczyk.dbtesting.db.RequeryHelper
import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity
import rx.observers.TestSubscriber

class RequeryTodoDatasourceSpec extends MySpecification {

    // direct access to database
    SingleEntityStore store

    RequeryHelper dataStore

    RequeryTodoDatasource datasource

    def "setup"() {
        dataStore = getTestRequeryHelper()
        store = dataStore.getDataStore()

        datasource = new RequeryTodoDatasource(dataStore)

        // each test will have empty database
        dataStore.recreateTables()
    }

    def "should insert new value"() {
        when:
        def test = datasource.addNewEntity("newEntityValue").test()

        then:
        test.assertCompleted()

        and: "make a query which is not exposed in datasource"
        store.select(TodoDbEntityEntity).get().toObservable().test().onNextEvents[0].getItemValue() == "newEntityValue"
        store.select(TodoDbEntityEntity).get().toObservable().test().onNextEvents[0].getCreatedAt().getTime() > 0L
    }

    def "should inform about new data"() {
        given:
        def subscriber = new TestSubscriber<List<TodoDbEntityEntity>>()

        when:
        datasource.getTodoEntitiesStream().subscribe(subscriber)

        then:
        subscriber.onNextEvents.size() == 1

        when:
        datasource.addNewEntity("newEntityValue1").test()

        then:
        subscriber.onNextEvents.size() == 2

        and:
        subscriber.onNextEvents[1][0].getItemValue() == "newEntityValue1"

        when:
        datasource.addNewEntity("newEntityValue2").test()

        then:
        subscriber.onNextEvents.size() == 3

        and:
        subscriber.onNextEvents[2][0].getItemValue() == "newEntityValue1"
        subscriber.onNextEvents[2][1].getItemValue() == "newEntityValue2"
    }
}
