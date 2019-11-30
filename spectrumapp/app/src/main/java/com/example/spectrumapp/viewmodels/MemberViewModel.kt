package com.example.spectrumapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.spectrumapp.db.entities.CompanyEntity
import com.example.spectrumapp.db.entities.MemberEntity
import com.example.spectrumapp.services.CompanyRepository

class MemberViewModel(application: Application, private val companyId: String): AndroidViewModel(application) {
        var liveData: LiveData<List<MemberEntity>>
        var mediatorLiveData =  MediatorLiveData<List<MemberEntity>>()
        var repository: CompanyRepository

    init {
        repository = CompanyRepository.getInstance(this.getApplication())
        liveData = repository.getMembers(companyId)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMembers(): MediatorLiveData<List<MemberEntity>>{
        return mediatorLiveData
    }

    fun memberFavorite(id: String, value: Boolean){
        repository.memberFavorite(id, value)
    }

    fun getCompanyMember(name: String) {
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMembers(name)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getAllMembers() {
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getAllMembers()

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMembersByCompany(id: String){
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMembers(id)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMemberByName(name: String) {
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMemberByName(name)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMemberByNameForCompany(id: String, name: String) {
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMemberByNameForCompany(id,name)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMemberByName(ase: Boolean){
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMemberByName(ase)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMemberByName(companyId: String,ase: Boolean){
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMemberByName(companyId,ase)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMemberByAge(ase: Boolean){
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMemberByAge(ase)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

    fun getMemberByAge(companyId: String,ase: Boolean){
        mediatorLiveData?.removeSource(liveData as LiveData<List<MemberEntity>>)
        liveData = repository.getMemberByAge(companyId,ase)

        mediatorLiveData?.addSource(liveData as LiveData<List<MemberEntity>>){
            mediatorLiveData?.value = it
        }
    }

}