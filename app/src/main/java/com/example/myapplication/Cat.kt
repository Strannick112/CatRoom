package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val nick: String,
    @ColumnInfo val age: Int,
    @ColumnInfo val gender: Boolean
)
