package com.example.spectrumapp.services.models

import com.google.gson.annotations.SerializedName

data class Company (
    @SerializedName("_id") val id: String,
    @SerializedName("company") val company: String,
    @SerializedName("website") val website: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("about") val about: String,
    @SerializedName("members") val members: List<Member>
)