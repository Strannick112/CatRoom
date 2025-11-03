package com.example.myapplication

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BreedRepository(private val breedDao: BreedDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val breedList: LiveData<List<Breed>> = breedDao.getAll()

    fun addBreed(breed: Breed) {
        coroutineScope.launch {
            breedDao.add(breed)
        }
    }

}