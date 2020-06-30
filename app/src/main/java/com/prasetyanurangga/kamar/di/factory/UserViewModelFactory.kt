package com.prasetyanurangga.kamar.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prasetyanurangga.kamar.repository.UserLocalRepository
import com.prasetyanurangga.kamar.viewmodel.UserViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserViewModelFactory @Inject constructor(private var userLocalRepository: UserLocalRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userLocalRepository) as T
    }
}