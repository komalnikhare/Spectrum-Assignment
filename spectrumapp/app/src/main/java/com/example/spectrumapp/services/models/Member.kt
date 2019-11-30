package com.example.spectrumapp.services.models

import com.google.gson.annotations.SerializedName

data class Member (
    @SerializedName("_id") val id: String,
    @SerializedName("age") val age: Int,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val name: Name
)