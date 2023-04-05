package com.example.test2.model

import com.google.gson.annotations.SerializedName

data class Words(
    @SerializedName("word") val words: String,
    @SerializedName("category") val category: String,
    @SerializedName("numLetters") val numLetters: Int,
    @SerializedName("numSyllables")val numSyllables: Int
)
