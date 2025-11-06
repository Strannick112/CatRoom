package com.example.myapplication

import androidx.room.Embedded
import androidx.room.Relation

data class BreedWithCats (
    @Embedded val breed: Breed,
    @Relation(
        parentColumn = "breedId",
        entityColumn = "catBreedId"
    ) val cats: List<Cat>
)
