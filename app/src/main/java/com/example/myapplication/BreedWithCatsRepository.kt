package com.example.myapplication

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BreedWithCatsRepository(private val breedWithCatsDao: BreedWithCatsDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val breedWithCatsList: LiveData<List<BreedWithCats>> = breedWithCatsDao.getAll()

//    fun addBreed(breed: Breed) {
//        coroutineScope.launch {
//            breedDao.add(breed)
//        }
//    }
}