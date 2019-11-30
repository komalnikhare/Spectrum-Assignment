package com.example.spectrumapp.services

import com.example.spectrumapp.services.models.Company
import retrofit2.Call
import retrofit2.http.GET

interface CompanyApi {

    @get:GET("get/Vk-LhK44U")
    val getCompanyList: Call<ArrayList<Company>>?
}