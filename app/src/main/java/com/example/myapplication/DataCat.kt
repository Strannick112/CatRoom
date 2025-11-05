package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Cat::class), (Breed::class)], version = 2)
abstract class DataCat: RoomDatabase() {

    abstract fun catDao(): CatDao

    abstract fun breedDao(): BreedDao

    abstract fun breedWithCatsDao(): BreedWithCatsDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: DataCat? = null
        fun getInstance(context: Context): DataCat {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataCat::class.java,
                        "Cat_database"

                    ).fallbackToDestructiveMigration(true).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
