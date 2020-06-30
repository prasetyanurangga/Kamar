package com.prasetyanurangga.kamar.di.module

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.prasetyanurangga.kamar.database.AppDatabase
import com.prasetyanurangga.kamar.database.model.User
import com.prasetyanurangga.kamar.util.Constanta
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class DatabaseModule() {

    lateinit var appDatabase: AppDatabase

    @Singleton
    @Provides
    fun appDatabase(application: Application): AppDatabase {
        appDatabase = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            Constanta.Database.dbName
        ).fallbackToDestructiveMigration().build()
        return appDatabase
    }


    @Singleton
    @Provides
    fun providesUserDAO(appDatabase: AppDatabase) = appDatabase.userDao()

}