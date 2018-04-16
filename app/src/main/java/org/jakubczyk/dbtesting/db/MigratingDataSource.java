package org.jakubczyk.dbtesting.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.requery.android.sqlite.DatabaseSource;
import io.requery.meta.EntityModel;

public class MigratingDataSource extends DatabaseSource {

    private DbMigrator dbMigrator;

    public MigratingDataSource(Context context, EntityModel model, int version, DbMigrator dbMigrator) {
        super(context, model, version);
        this.dbMigrator = dbMigrator;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbMigrator.StatementRunner runner = new AndroidDbMigratorRunner(db);

        dbMigrator.migrate(runner, oldVersion, newVersion);

        super.onUpgrade(db, oldVersion, newVersion);
    }
}
