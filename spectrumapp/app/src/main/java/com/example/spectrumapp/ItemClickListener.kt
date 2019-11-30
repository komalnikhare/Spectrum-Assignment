package com.example.spectrumapp

import com.example.spectrumapp.db.entities.MemberEntity
import java.util.*

interface ItemClickListener {

    fun onItemClicked(which: Int, id: String, value: Boolean)
}