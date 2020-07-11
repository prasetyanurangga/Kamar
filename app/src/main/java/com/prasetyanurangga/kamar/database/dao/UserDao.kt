package com.prasetyanurangga.kamar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.prasetyanurangga.kamar.database.model.User
import com.prasetyanurangga.kamar.util.Constanta

@Dao
interface UserDao {

    @Query( "SELECT * FROM ${Constanta.Database.User}" )
    fun getAll(): LiveData<List<User>>

    @Query( "SELECT * FROM ${Constanta.Database.User} WHERE uid = :uid" )
    fun getByUid(uid: Int): User

    @Insert
    fun saveUser(vararg user: User)

    @Update
    fun updateUser(vararg user: User)

    @Delete
    fun deleteUser(vararg user: User)



}