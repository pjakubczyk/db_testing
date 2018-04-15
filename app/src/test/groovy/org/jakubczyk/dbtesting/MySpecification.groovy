package org.jakubczyk.dbtesting

import io.requery.converter.EnumStringConverter
import io.requery.sql.Configuration
import io.requery.sql.SchemaModifier
import io.requery.sql.TableCreationMode
import io.requery.sql.platform.SQLite
import org.jakubczyk.dbtesting.common.IoScheduler
import org.jakubczyk.dbtesting.common.MainScheduler
import org.jakubczyk.dbtesting.db.RequeryDatastore
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.sqlite.SQLiteConfig
import org.sqlite.SQLiteDataSource
import rx.Scheduler
import rx.schedulers.TestScheduler
import spock.lang.Specification

import java.sql.SQLException
import java.sql.Statement

class MySpecification extends Specification {

    def testIoScheduler = new TestIoScheduler(new TestScheduler());
    def testMainScheduler = new TestMainScheduler(new TestScheduler());

    def triggerScheduler() {
        testIoScheduler.get().triggerActions()
        testMainScheduler.get().triggerActions()
    }

    static class TestIoScheduler extends IoScheduler {
        TestIoScheduler(Scheduler scheduler) {
            super(scheduler)
        }

        public TestScheduler get() {
            return scheduler;
        }
    }

    static class TestMainScheduler extends MainScheduler {
        TestMainScheduler(Scheduler scheduler) {
            super(scheduler)
        }

        public TestScheduler get() {
            return scheduler;
        }
    }

    // DB STUFF
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    def getDbSource() {
        def persistableRepository = getPersistableRepository()

        def configuration = persistableRepository.getConfiguration()

        dropTables(configuration)
        createTables(configuration)

        return persistableRepository.getDataStore()
    }

    RequeryDatastore getPersistableRepository() {
        def builder = new RequeryDatastore.Builder(sqlLiteSource())
        builder.platform(new SQLite())

        return builder.build()
    }

    void createTables(Configuration configuration) {
        SchemaModifier tables = new SchemaModifier(configuration);
        TableCreationMode mode = TableCreationMode.CREATE;
        // Uncomment to see all sql creation statements
//        System.out.println(tables.createTablesString(mode));
        tables.createTables(mode);
    }

    void dropTables(Configuration configuration) {
        SchemaModifier tables = new SchemaModifier(configuration);
        tables.dropTables();
    }

    def sqlLiteSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + folder.newFile().absolutePath);
        SQLiteConfig config = new SQLiteConfig();
        config.setDateClass("TEXT");
        dataSource.setConfig(config);
        try {
            Statement statement = dataSource.getConnection().createStatement()
            statement.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
    
}