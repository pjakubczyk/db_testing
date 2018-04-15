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
}
