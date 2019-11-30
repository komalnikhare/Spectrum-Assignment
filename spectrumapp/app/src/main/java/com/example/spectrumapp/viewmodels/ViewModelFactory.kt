package com.example.restcountries.UI.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.spectrumapp.viewmodels.CompanyViewModel
import com.example.spectrumapp.viewmodels.MemberViewModel


class ViewModelFactory(private val application: Application, private val which: Int) : ViewModelProvider.Factory {

    private var  companyId: String? = null

    constructor( application: Application, which: Int,  companyId: String ): this(application, which){
        this.companyId = companyId
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when(which){
            1 -> return CompanyViewModel(application) as T
            2 -> return  MemberViewModel(application, companyId!!) as T
            else -> return CompanyViewModel(application) as T
        }
    }
}