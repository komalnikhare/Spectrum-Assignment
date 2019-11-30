package com.example.spectrumapp.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "member",
    foreignKeys = [ForeignKey(
        entity = CompanyEntity::class,
        parentColumns = ["id"], childColumns = ["companyId"]
    )]
)
data class MemberEntity(
    @PrimaryKey
    val id: String,
    val age: Int,
    val name: String,
    val email: String,
    val phone: String,
    val companyId: String,
    var favorite: Boolean
)