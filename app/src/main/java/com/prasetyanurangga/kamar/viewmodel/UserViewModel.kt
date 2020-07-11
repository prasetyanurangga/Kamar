package com.prasetyanurangga.kamar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.prasetyanurangga.kamar.database.model.User
import com.prasetyanurangga.kamar.repository.UserLocalRepository


class UserViewModel (private var userLocalRepository: UserLocalRepository): ViewModel(){
    var getAllUser: LiveData<List<User>> = userLocalRepository.getUsers()
    var isLoading: Boolean = false;
    
    fun saveUser(user: User): LiveData<List<User>>{
        userLocalRepository.addUser(user)
        return userLocalRepository.getUsers();
    }

    fun deleteUser(user: User): LiveData<List<User>>{
        userLocalRepository.deleteUser(user)
        return userLocalRepository.getUsers();
    }

    fun updateUser(user: User): LiveData<List<User>>{
        userLocalRepository.updateUser(user)
        return userLocalRepository.getUsers();
    }
}