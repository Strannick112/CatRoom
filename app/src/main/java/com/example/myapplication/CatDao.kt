package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatDao {
    @Query("SELECT * FROM Cat")
    fun getAll(): LiveData<List<Cat>>

    @Query("SELECT * FROM Cat WHERE age > :olderThenAge")
    fun getCatsOlderThen(olderThenAge: Int): LiveData<List<Cat>>

    @Query("SELECT * FROM Cat WHERE age > :olderThenAge ORDER BY age LIMIT 1")
    fun getTheOlderCat(olderThenAge: Int): LiveData<Cat?>

    @Insert
    fun add(vararg cats: Cat)

    @Delete
    fun delete(cat: Cat)

    @Query("DELETE FROM Cat WHERE id=:id")
    fun deleteById(id: Int)
}
