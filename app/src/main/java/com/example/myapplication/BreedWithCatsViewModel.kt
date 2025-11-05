package com.example.myapplication

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class BreedWithCatsViewModel(application: Application) : ViewModel() {

    val breedWithCatsList: LiveData<List<BreedWithCats>>
    private val repository: BreedWithCatsRepository
//    var id by mutableStateOf(0)
//    var title by mutableStateOf("")
//    var description by mutableStateOf("")

    init {
        val breedDb = DataCat.getInstance(application)
        val breedDao = breedDb.breedWithCatsDao()
        repository = BreedWithCatsRepository(breedDao)
        breedWithCatsList = repository.breedWithCatsList
    }

//    fun changeId(value: String){
//        id = value.toIntOrNull() ?: id
//    }
//
//    fun changeTitle(value: String){
//        title = value
//    }
//
//    fun changeDescription(value: String){
//        description = value
//    }
//
//    fun add() {
//        repository.addBreed(
//            Breed(
//                title = title,
//                description = description
//            )
//        )
//    }

}