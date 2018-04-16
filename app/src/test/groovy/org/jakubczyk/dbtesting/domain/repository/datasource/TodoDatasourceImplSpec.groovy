package org.jakubczyk.dbtesting.domain.repository.datasource

import io.requery.rx.SingleEntityStore
import org.jakubczyk.dbtesting.MySpecification
import org.jakubczyk.dbtesting.db.RequeryDatastore
import org.jakubczyk.dbtesting.db.model.TodoDbEntityEntity

class TodoDatasourceImplSpec extends MySpecification {

    // direct access to database
    SingleEntityStore store

    RequeryDatastore dataStore

    TodoDatasourceImpl datasource

    def "setup"() {
        dataStore = getPersistableRepository()
        store = dataStore.getDataStore()

        datasource = new TodoDatasourceImpl(dataStore)

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
    }

    def "should query for all todo items"() {
        given: "add few item"
        datasource.addNewEntity("newEntityValue1").test()
        datasource.addNewEntity("newEntityValue2").test()
        datasource.addNewEntity("newEntityValue3").test()

        when:
        def test = datasource.getTodoEntitiesStream().test()

        then:
        test.onNextEvents.size() == 1

        and: "the collection has 3 elements"
        test.onNextEvents[0].size() == 3
        test.onNextEvents[0][0].getItemValue() == "newEntityValue1"
        test.onNextEvents[0][1].getItemValue() == "newEntityValue2"
        test.onNextEvents[0][2].getItemValue() == "newEntityValue3"
    }
}
