package org.jakubczyk.dbtesting.db

import io.requery.rx.SingleEntityStore
import io.requery.sql.Configuration
import org.jakubczyk.dbtesting.MySpecification
import spock.lang.Unroll

class DbMigratorSpec extends MySpecification {

    DbMigrator dbMigrator = new DbMigrator()

    def entityStore
    def configuration

    def latestSchema

    def "setup"() {

        // prepare DB objects
        def persistableRepository = getPersistableRepository()

        entityStore = persistableRepository.getDataStore()
        configuration = persistableRepository.getConfiguration()

        // read schema from Requery (latest)
        dropTables(configuration)
        createTables(configuration)

        latestSchema = readDbSchema(entityStore)
    }

    def "should check all migrations"() {
        given: "clear db schema"
        dropTables(configuration)

        and: "init database with schema 1"
        setupInitialState(configuration)

        when: "run all migrations"
        dbMigrator.migrate(
                { sql -> runStatement(configuration, sql) },
                RequeryHelper.INITIAL_DB_SCHEMA_VERSION,
                RequeryHelper.SCHEMA_VERSION
        )

        and: "read current text schema"
        def schemaAfter = readDbSchema(entityStore)

        then: "compare with schema latest"
        schemaAfter == latestSchema
    }

    @Unroll
    def "should do migration from db version #version"() {
        given: "clear db schema"
        dropTables(configuration)

        and: "init database with schema 1"
        setupInitialState(configuration)

        and: "apply previous migrations in order"
        dbMigrator
                .allMigrations
                .findAll { migration -> migration.versionToMigrate <= version }
                .sort { it.versionToMigrate }
                .each { migration -> migration.migrate({ sql -> runStatement(configuration, sql) }) }

        when: "run migrator"
        dbMigrator.migrate(
                { sql -> runStatement(configuration, sql) },
                version,
                RequeryHelper.SCHEMA_VERSION
        )

        and: "read current text schema"
        def schemaAfter = readDbSchema(entityStore)

        then: "compare with schema"
        schemaAfter == latestSchema

        where:
        version << (RequeryHelper.INITIAL_DB_SCHEMA_VERSION..RequeryHelper.SCHEMA_VERSION)
    }

    def unparcelSqlTable(String sqlStatement) {
        def sqlTable = new SqlTable()

        sqlTable.name = (sqlStatement - "CREATE TABLE ").split(" ")[0]

        // remove all brackets
        // varchar(255) becomes varchar255 but for testing reason it's fine
        def statementWithoutName = ((sqlStatement - "CREATE TABLE ") - (sqlTable.name + " ")).replaceAll("""\\(|\\)""", "");

        def statementParams = statementWithoutName.split(",").collect { it.trim() } as Set

        sqlTable.indexes = statementParams.findAll { it.startsWith("foreign key") } as Set

        sqlTable.columns = statementParams - sqlTable.indexes

        sqlTable
    }

    def setupInitialState(Configuration configuration) {
        def statement = configuration.getConnectionProvider().getConnection().createStatement()
        def dbInitialState = this.getClass().getResource("/db_schema_1.sql").text
        dbInitialState.split("\n").each { statement.addBatch(it) }
        statement.executeBatch()
    }

    def runStatement(Configuration configuration, String sql) {
        def statement = configuration.getConnectionProvider().getConnection().createStatement()
        statement.execute(sql)
    }

    def readDbSchema(SingleEntityStore entityStore) {
        return entityStore
                .raw("SELECT sql FROM sqlite_master;")
                .toObservable()
                .toList()
                .toBlocking()
                .first()
                .collect { it.get("sql") }
                .findAll { it != null }
                .collect { unparcelSqlTable(it) }
                .toSet()
    }


    class SqlTable {
        String name
        Set columns
        Set indexes

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            SqlTable sqlTable = (SqlTable) o

            if (columns != sqlTable.columns) return false
            if (indexes != sqlTable.indexes) return false
            if (name != sqlTable.name) return false

            return true
        }

        int hashCode() {
            int result
            result = (name != null ? name.hashCode() : 0)
            result = 31 * result + (columns != null ? columns.hashCode() : 0)
            result = 31 * result + (indexes != null ? indexes.hashCode() : 0)
            return result
        }


        @Override
        public String toString() {
            return "SqlTable{" +
                    "name='" + name + '\'' +
                    ", columns=" + columns +
                    ", indexes=" + indexes +
                    '}';
        }
    }
}
