package org.jakubczyk.dbtesting.db;

import android.content.Context;

import org.jakubczyk.dbtesting.db.model.Models;

import java.util.Set;

import javax.sql.CommonDataSource;

import io.requery.Persistable;
import io.requery.android.DefaultMapping;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.cache.EmptyEntityCache;
import io.requery.converter.EnumStringConverter;
import io.requery.rx.RxSupport;
import io.requery.rx.SingleEntityStore;
import io.requery.sql.Configuration;
import io.requery.sql.ConfigurationBuilder;
import io.requery.sql.EntityDataStore;
import io.requery.sql.Platform;
import io.requery.sql.SchemaModifier;
import io.requery.sql.TableCreationMode;
import io.requery.sql.platform.SQLite;

public class RequeryDatastore {

    // Don't change this values.
    // It marks from which DB version we started to migrate the schema
    public static final int INITIAL_DB_SCHEMA_VERSION = 1;

    public final static int SCHEMA_VERSION = 1;

    private final SingleEntityStore<Persistable> dataStore;
    private final Configuration configuration;

    private RequeryDatastore(SingleEntityStore<Persistable> dataStore, Configuration configuration) {
        this.dataStore = dataStore;
        this.configuration = configuration;
    }

    public SingleEntityStore<Persistable> getDataStore() {
        return dataStore;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void recreateTables() {
        SchemaModifier schemaModifier = new SchemaModifier(configuration);
        schemaModifier.createTables(TableCreationMode.DROP_CREATE);
    }

    public static class Builder {

        private Platform platform = new SQLite();
        private ConfigurationBuilder configurationBuilder;

        // Android version
        public Builder(Context context) {
            DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, SCHEMA_VERSION);
            source.setTableCreationMode(TableCreationMode.CREATE_NOT_EXISTS);
            source.setLoggingEnabled(true);

            configurationBuilder = new ConfigurationBuilder(source, Models.DEFAULT);
        }

        // Java version
        public Builder(CommonDataSource commonDataSource) {
            configurationBuilder = new ConfigurationBuilder(commonDataSource, Models.DEFAULT);
        }

        // only used in tests because io.requery.android.sqlite.DatabaseSource set in Android to SQLite
        public Builder platform(Platform platform) {
            this.platform = platform;
            configurationBuilder.setPlatform(platform);

            return this;
        }


        public RequeryDatastore build() {
            configurationBuilder.setEntityCache(new EmptyEntityCache());
            Configuration configuration = configurationBuilder.build();
            EntityDataStore<Persistable> store = new EntityDataStore<>(configuration);

            return new RequeryDatastore(RxSupport.toReactiveStore(store), configuration);
        }

    }
}
