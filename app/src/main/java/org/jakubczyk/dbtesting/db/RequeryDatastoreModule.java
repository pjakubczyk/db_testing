package org.jakubczyk.dbtesting.db;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RequeryDatastoreModule {

    @Provides
    @Singleton
    RequeryDatastore provideDataStore(Context context) {
        RequeryDatastore.Builder builder = new RequeryDatastore.Builder(context);

        return builder.build();
    }


}
