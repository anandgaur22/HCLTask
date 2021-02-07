package com.anandgaur.hcltask.utils

import android.annotation.SuppressLint
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object FormatedMethod {

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(date: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("MMM dd, yyyy")
        val formattedDate = parser.parse(date)?.let { formatter.format(it) }

        return formattedDate
    }

    @SuppressLint("SimpleDateFormat")
    fun getYearRelease(date: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy")
        val formattedYear = parser.parse(date)?.let { formatter.format(it) }

        return formattedYear
    }

    fun roundOffDecimal(number: Double): String {
        val df = DecimalFormat("#,###.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number)
    }

    fun roundOffInt(number: Int): String {
        val df = DecimalFormat("#,###.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number)
    }

    fun getPopular(number: Double): String {
        val result = (number / 10) * 100
        return roundOffDecimal(result)
    }

}