package com.anandgaur.hcltask.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "createdbyentities",
    primaryKeys = ["createdId", "moviesId"],
    foreignKeys = [ForeignKey(
        entity = MoviesEntity::class,
        parentColumns = ["moviesId"],
        childColumns = ["moviesId"]
    )],
    indices = [Index(value = ["createdId"]),
        Index(value = ["moviesId"])]
)
data class CreatedByEntity(

    @NonNull
    @ColumnInfo(name = "createdId")
    var createdId: Int,

    @NonNull
    @ColumnInfo(name = "moviesId")
    var moviesId: Int,

    @NonNull
    @ColumnInfo(name = "name")
    var name: String
)