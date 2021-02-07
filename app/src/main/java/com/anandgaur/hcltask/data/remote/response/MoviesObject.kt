package com.anandgaur.hcltask.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MoviesObject(

    @field:SerializedName("id")
    val moviesId: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("original_language")
    val originalLanguage: String,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("vote_count")
    val voteCount: Int,

    @field:SerializedName("budget")
    val budget: Int,

    @field:SerializedName("revenue")
    val revenue: Int,

    @field:SerializedName("tagline")
    val tagline: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("genres")
    val genres: List<GenresItem>? = java.util.ArrayList(),

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("last_air_date")
    val lastAirDate: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("created_by")
    val createdBy: List<CreatedByItem>,

    @field:SerializedName("spoken_languages")
    val spoken_languages: List<LanguageResponse>

) : Parcelable