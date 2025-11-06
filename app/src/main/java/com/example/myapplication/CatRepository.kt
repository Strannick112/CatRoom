package com.example.myapplication

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatRepository(private val catDao: CatDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val catList: LiveData<List<Cat>> = catDao.getAll()

    fun addCat(cat: Cat) {
        coroutineScope.launch {
            catDao.add(cat)
        }
    }

    fun deleteCat(id: Int) {
        coroutineScope.launch{
            catDao.deleteById(id)
        }
    }

    fun updateCat(id: Int, nick: String, age: Int, gender: Boolean, catBreedId: Int){
        coroutineScope.launch {
            catDao.updateById(id, nick, age, gender, catBreedId)
        }
    }
}