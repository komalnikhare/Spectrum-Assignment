package com.example.spectrumapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class CompanyEntity (
    @PrimaryKey
    val id: String,
    val company: String,
    val website: String,
    val logo: String,
    val about: String,
    var favorite: Boolean,
    var follow: Boolean
)