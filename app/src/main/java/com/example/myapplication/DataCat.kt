package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Cat::class)], version = 1)
abstract class DataCat: RoomDatabase() {

    abstract fun catDao(): CatDao

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
