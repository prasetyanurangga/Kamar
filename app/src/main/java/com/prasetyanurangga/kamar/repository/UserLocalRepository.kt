package com.prasetyanurangga.kamar.repository

import androidx.lifecycle.LiveData
import com.prasetyanurangga.kamar.database.AppDatabase
import com.prasetyanurangga.kamar.database.dao.UserDao
import com.prasetyanurangga.kamar.database.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLocalRepository(appDatabase: AppDatabase) {

    private var userDao: UserDao = appDatabase.userDao()

    fun getUsers(): LiveData<List<User>>{
        return userDao.getAll()
    }

    fun addUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            userDao.saveUser(user)
        }
    }

    fun deleteUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteUser(user)
        }
    }

}