package com.anandgaur.hcltask.data.local.entity

import androidx.annotation.NonNull
import androidx.room.*

@Entity(
    tableName = "directorentities",
    primaryKeys = ["directorId", "moviesId"],
    foreignKeys = [ForeignKey(
        entity = MoviesEntity::class,
        parentColumns = ["moviesId"],
        childColumns = ["moviesId"]
    )],
    indices = [Index(value = ["directorId"]),
        Index(value = ["moviesId"])]
)
data class DirectorEntity(

    @NonNull
    @ColumnInfo(name = "directorId")
    var directorId: Int,

    @NonNull
    @ColumnInfo(name = "moviesId")
    var moviesId: Int,

    @NonNull
    @ColumnInfo(name = "name")
    var name: String,

    @NonNull
    @ColumnInfo(name = "job")
    var job: String
)
