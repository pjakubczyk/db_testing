package org.jakubczyk.dbtesting.db;

public interface DbMigration {

    int getVersionToMigrate();

    void migrate(final DbMigrator.StatementRunner statementRunner);
}
