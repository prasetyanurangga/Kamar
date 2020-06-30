package com.prasetyanurangga.kamar.di.module

import com.prasetyanurangga.kamar.database.AppDatabase
import com.prasetyanurangga.kamar.repository.UserLocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class KamarModule {

    @Singleton
    @Provides
    fun userLocalRepository(appDatabase: AppDatabase): UserLocalRepository{
        return UserLocalRepository(appDatabase)
    }

}