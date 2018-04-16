package org.jakubczyk.dbtesting.db;

import org.jakubczyk.dbtesting.db.migration.Migration2;

public class DbMigrator {

    DbMigration[] allMigrations = new DbMigration[]{
            new Migration2()
    };

    public void migrate(StatementRunner statementRunner, int oldVersion, int newVersion) {
        for (DbMigration dbMigration : allMigrations) {
            if (dbMigration.getVersionToMigrate() > oldVersion) {
                dbMigration.migrate(statementRunner);
            }
        }
    }

    public interface StatementRunner {

        void runStatement(String sql);
    }

}
