package org.jakubczyk.dbtesting.db.migration;

import org.jakubczyk.dbtesting.db.DbMigration;
import org.jakubczyk.dbtesting.db.DbMigrator;

public class Migration2 implements DbMigration{
    @Override
    public int getVersionToMigrate() {
        return 2;
    }

    @Override
    public void migrate(DbMigrator.StatementRunner statementRunner) {
        statementRunner.runStatement("ALTER TABLE TodoDbEntity ADD COLUMN createdAt date;");
    }
}
