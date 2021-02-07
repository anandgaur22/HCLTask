package com.anandgaur.hcltask.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    tableName = "genresentities",
    primaryKeys = ["genreId", "moviesId"],
    foreignKeys = [ForeignKey(
        entity = MoviesEntity::class,
        parentColumns = ["moviesId"],
        childColumns = ["moviesId"]
    )],
    indices = [Index(value = ["genreId"]),
        Index(value = ["moviesId"])]
)
data class GenresEntity(

    @NonNull
    @ColumnInfo(name = "genreId")
    var genreId: Int,

    @NonNull
    @ColumnInfo(name = "moviesId")
    var moviesId: Int,

    @NonNull
    @ColumnInfo(name = "name")
    var name: String

)