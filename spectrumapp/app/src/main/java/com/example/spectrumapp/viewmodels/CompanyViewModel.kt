package com.example.spectrumapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.spectrumapp.db.entities.CompanyEntity
import com.example.spectrumapp.services.CompanyRepository

class CompanyViewModel(application: Application): AndroidViewModel(application) {
        var liveData: LiveData<List<CompanyEntity>>
        var mediatorLiveData =  MediatorLiveData<List<CompanyEntity>>()
        var repository: CompanyRepository

    init {
        repository = CompanyRepository.getInstance(this.getApplication())
        repository.getCompanies()
        liveData = repository.getCompany()

        mediatorLiveData?.addSource(liveData as LiveData<List<CompanyEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getCompanies(): MediatorLiveData<List<CompanyEntity>>{
        return mediatorLiveData
    }

    fun getCompanyByDescending() {
        mediatorLiveData?.removeSource(liveData as LiveData<List<CompanyEntity>>)
        liveData = repository.getCompanyByDescending()

        mediatorLiveData?.addSource(liveData as LiveData<List<CompanyEntity>>) {
            mediatorLiveData?.value = it
        }

    }

    fun getCompanyByAscending() {
        mediatorLiveData?.removeSource(liveData as LiveData<List<CompanyEntity>>)
        liveData = repository.getCompanyByAscending()

        mediatorLiveData?.addSource(liveData as LiveData<List<CompanyEntity>>) {
            mediatorLiveData?.value = it
        }

    }

    fun getCompanyByName(name: String) {
        mediatorLiveData?.removeSource(liveData as LiveData<List<CompanyEntity>>)
        liveData = repository.getCompanyByName(name)

        mediatorLiveData?.addSource(liveData as LiveData<List<CompanyEntity>>) {
            mediatorLiveData?.value = it
        }
    }

    fun companyFavorite(id: String, value: Boolean){
        repository.companyFavorite(id, value)
    }

    fun companyFollow(id: String, value: Boolean){
        repository.companyFollow(id, value)
    }
}