package com.example.myapplication

import androidx.room.Embedded
import androidx.room.Relation

data class BreedWithCats (
    @Embedded val breed: Breed,
    @Relation(
        parentColumn = "breedId",
        entityColumn = "catId"
    ) val cats: List<Cat>
)
