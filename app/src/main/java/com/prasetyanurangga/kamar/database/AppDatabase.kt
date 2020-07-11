package com.prasetyanurangga.kamar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prasetyanurangga.kamar.database.dao.UserDao
import com.prasetyanurangga.kamar.database.model.User

@Database(
    entities = [
        User::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

}