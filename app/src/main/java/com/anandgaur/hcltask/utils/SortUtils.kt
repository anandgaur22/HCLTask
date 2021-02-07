package com.anandgaur.hcltask.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    const val DEFAULT = "Default"
    const val POPULAR = "Popular"

    fun getMoviesSortedQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM moviesentities where isMovies = 1 ")
        if (filter == DEFAULT) {
            simpleQuery.append("")
        }  else if (filter == POPULAR) {
            simpleQuery.append("ORDER BY popularity DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}