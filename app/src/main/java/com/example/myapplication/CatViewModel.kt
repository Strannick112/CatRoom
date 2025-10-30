package com.example.myapplication

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class CatViewModel(application: Application) : ViewModel() {

    val catList: LiveData<List<Cat>>
    private val repository: CatRepository
    var catNick by mutableStateOf("")
    var catAge by mutableStateOf(0)
    var catGender by mutableStateOf(true)

    init {
        val catDb = DataCat.getInstance(application)
        val catDao = catDb.catDao()
        repository = CatRepository(catDao)
        catList = repository.catList
    }

    fun changeName(value: String){
        catNick = value
    }

    fun changeAge(value: String){
        catAge = value.toIntOrNull() ?: catAge
    }

    fun changeGender(value: String){
        catGender = value.toBooleanStrictOrNull() ?: catGender
    }

    fun addCat() {
        repository.addCat(
            Cat(
                nick = catNick,
                age = catAge,
                gender = catGender
            )
        )
    }

    fun deleteUser(id: Int) {
        repository.deleteCat(id)
    }

}