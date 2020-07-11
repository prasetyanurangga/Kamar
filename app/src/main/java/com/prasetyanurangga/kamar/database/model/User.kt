package com.prasetyanurangga.kamar.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prasetyanurangga.kamar.util.Constanta

@Entity(
    tableName = Constanta.Database.User
)
data class User (
    @PrimaryKey(
        autoGenerate = true
    )
    val uid: Int?,

    @ColumnInfo( name = "name" )
    val name: String,

    @ColumnInfo( name = "email" )
    val email: String
)