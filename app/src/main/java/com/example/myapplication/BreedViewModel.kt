package com.example.myapplication

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class BreedViewModel(application: Application) : ViewModel() {

    val breedList: LiveData<List<Breed>>
    private val repository: BreedRepository
    var id by mutableStateOf(0)
    var title by mutableStateOf("")
    var description by mutableStateOf("")

    init {
        val breedDb = DataCat.getInstance(application)
        val breedDao = breedDb.breedDao()
        repository = BreedRepository(breedDao)
        breedList = repository.breedList
    }

    fun changeId(value: String){
        id = value.toIntOrNull() ?: id
    }

    fun changeTitle(value: String){
        title = value
    }

    fun changeDescription(value: String){
        description = value
    }

    fun add() {
        repository.addBreed(
            Breed(
                title = title,
                description = description
            )
        )
    }

}