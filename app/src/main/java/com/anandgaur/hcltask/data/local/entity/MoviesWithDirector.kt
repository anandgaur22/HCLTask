package com.anandgaur.hcltask.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class MoviesWithDirector(
    @Embedded
    var mMovies: MoviesEntity,

    @Relation(parentColumn = "moviesId", entityColumn = "moviesId")
    var mDirector: List<DirectorEntity>
)