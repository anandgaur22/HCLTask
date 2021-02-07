package com.anandgaur.hcltask.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesentities")
data class MoviesEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "moviesId")
    var moviesId: Int,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "original_language")
    var originalLanguage: String? = null,

    @ColumnInfo(name = "language")
    var language: String? = null,

    @ColumnInfo(name = "release_date")
    var releaseDate: String? = null,

    @ColumnInfo(name = "popularity")
    var popularity: Double? = null,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double? = null,

    @ColumnInfo(name = "vote_count")
    var voteCount: Int? = null,

    @ColumnInfo(name = "budget")
    var budget: Int? = null,

    @ColumnInfo(name = "revenue")
    var revenue: Int? = null,

    @ColumnInfo(name = "tagline")
    var tagline: String? = null,

    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "first_air_date")
    var firstAirDate: String? = null,

    @ColumnInfo(name = "last_air_date")
    var lastAirDate: String? = null,

    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "isMovies")
    var isMovies: Boolean = false,

    @ColumnInfo(name = "isTvshows")
    var isTvshows: Boolean = false
)