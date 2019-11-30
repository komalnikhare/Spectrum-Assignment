package com.example.spectrumapp.services

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.restcountries.service.RetrofitService
import com.example.spectrumapp.db.CompanyDb
import com.example.spectrumapp.db.dao.CompanyDao
import com.example.spectrumapp.db.entities.CompanyEntity
import com.example.spectrumapp.db.entities.MemberEntity
import com.example.spectrumapp.services.models.Company
import com.example.spectrumapp.services.models.Member
import retrofit2.Call
import retrofit2.Response

class CompanyRepository private constructor(){
    companion object{
        private var repository: CompanyRepository? = null
        private var companyApi: CompanyApi? = null
        private var context: Context? = null
        private var db: CompanyDb? = null

        fun getInstance(context: Context): CompanyRepository{
            if (repository == null){
                this.context = context
                db = CompanyDb.getDb(context)
                repository = CompanyRepository()

            }
            return repository as CompanyRepository
        }

    }

    init {
        companyApi = RetrofitService.createService(CompanyApi::class.java)
    }

    fun getCompanies(){
        val companies = ArrayList<Company>()
        companyApi?.getCompanyList?.enqueue(object : retrofit2.Callback<ArrayList<Company>> {

            override fun onResponse(
                call: Call<ArrayList<Company>>,
                response: Response<ArrayList<Company>>) {

                if (response.isSuccessful){
                    companies.addAll(response.body()!!)
                    val companyEntity = ArrayList<CompanyEntity>()
                    val memberEntity = ArrayList<MemberEntity>()
                    for (c: Company in companies){
                        val company = CompanyEntity(c.id, c.company,c.website, c.logo,c.about,
                            favorite = false,
                            follow = false
                        )
                        companyEntity.add(company)
                        for (m: Member in c.members){
                            val member = MemberEntity(m.id, m.age,
                                "${m.name.first} ${m.name.last}",m.email, m.phone,c.id, favorite = false)
                            memberEntity.add(member)
                        }
                    }
                    addAsynTask(companyEntity, memberEntity).execute()
                }
            }

            override fun onFailure(call: Call<ArrayList<Company>>, t: Throwable) {

            }
        })

    }

    class addAsynTask(companies: List<CompanyEntity>, members: List<MemberEntity>): AsyncTask<Void, Void, Void>(){

        private val companies = companies
        private val members = members
        override fun doInBackground(vararg p0: Void?): Void? {
            db?.daoCompany()?.insert(companies)
            db?.daoMembers()?.insert(members)
            return null
        }
    }

    fun getCompany(): LiveData<List<CompanyEntity>>{
        return db?.daoCompany()!!.getCompanies()
    }

    fun getMembers(id: String): LiveData<List<MemberEntity>>{
        return  db?.daoMembers()!!.getMembers(id)
    }

    fun getCompanyByAscending(): LiveData<List<CompanyEntity>>{
        return db?.daoCompany()!!.getCompanyByAscending()
    }

    fun getCompanyByDescending(): LiveData<List<CompanyEntity>>{
        return db?.daoCompany()!!.getCompanyByDescending()
    }

    fun getCompanyByName(name: String): LiveData<List<CompanyEntity>>{
        return db?.daoCompany()!!.getCompanyByName(name)
    }

    fun companyFavorite(id: String, value: Boolean){
        db?.daoCompany()!!.updateFavorite(id,value)
    }

    fun companyFollow(id: String, value: Boolean){
        db?.daoCompany()!!.updateFollow(id,value)
    }

    fun memberFavorite(id: String, value: Boolean){
        db?.daoMembers()!!.updateFavorite(id, value)
    }

    fun getMemberByName(name: String): LiveData<List<MemberEntity>>{
        return db?.daoMembers()!!.getMemberByName(name)
    }

    fun getMemberByNameForCompany(id: String, name: String): LiveData<List<MemberEntity>>{
        return db?.daoMembers()!!.getMemberByNameForCompany(id,name)
    }

    fun getAllMembers(): LiveData<List<MemberEntity>>{
        return db?.daoMembers()!!.getAllMembers()
    }


    fun getMemberByName(ase: Boolean): LiveData<List<MemberEntity>>{
        if (ase)
            return db?.daoMembers()!!.getMemberByNameAsc()
        else
            return db?.daoMembers()!!.getMemberByNameDesc()
    }

    fun getMemberByName(companyId: String,ase: Boolean): LiveData<List<MemberEntity>>{
        if (ase)
            return db?.daoMembers()!!.getMemberByNameAsc(companyId)
        else
            return db?.daoMembers()!!.getMemberByNameDesc(companyId)
    }

    fun getMemberByAge(ase: Boolean): LiveData<List<MemberEntity>>{
        if (ase)
            return db?.daoMembers()!!.getMemberByAgeAsc()
        else
            return db?.daoMembers()!!.getMemberByAgeDesc()
    }

    fun getMemberByAge(companyId: String,ase: Boolean): LiveData<List<MemberEntity>>{
        if (ase)
            return db?.daoMembers()!!.getMemberByAgeAsc(companyId)
        else
            return db?.daoMembers()!!.getMemberByAgeDesc(companyId)
    }
}