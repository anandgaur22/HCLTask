package com.anandgaur.hcltask.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageResponse(

	@field:SerializedName("iso_639_1")
	val iso6391: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("english_name")
	val englishName: String
): Parcelable
