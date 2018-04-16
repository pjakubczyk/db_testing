package org.jakubczyk.dbtesting.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AndroidDbMigratorRunner implements DbMigrator.StatementRunner {

    private SQLiteDatabase db;

    public AndroidDbMigratorRunner(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void runStatement(String sql) {
        Log.d("miration", sql);
        db.execSQL(sql);
    }
}