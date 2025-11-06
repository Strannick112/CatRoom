package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BreedWithCatsDao {
    @Transaction
    @Query("SELECT * FROM Breed")
    fun getAll(): LiveData<List<BreedWithCats>>
}
