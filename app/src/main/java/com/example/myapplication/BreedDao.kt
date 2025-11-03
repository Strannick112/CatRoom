package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BreedDao {
    @Query("SELECT * FROM Breed")
    fun getAll(): LiveData<List<Breed>>

    @Insert
    fun add(vararg breeds: Breed)
}
