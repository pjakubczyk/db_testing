package org.jakubczyk.dbtesting.db;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RequeryDatastoreModule {

    @Provides
    @Singleton
    RequeryHelper provideDataStore(Context context) {
        RequeryHelper.Builder builder = new RequeryHelper.Builder(context);

        return builder.build();
    }


}
