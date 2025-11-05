package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Breed (
    @PrimaryKey(autoGenerate = true) val breedId: Int = 0,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String
)
