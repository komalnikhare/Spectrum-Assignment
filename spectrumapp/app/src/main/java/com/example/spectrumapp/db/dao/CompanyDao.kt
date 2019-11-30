package com.example.spectrumapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spectrumapp.db.entities.CompanyEntity

@Dao
interface CompanyDao {
    @Query("select * from company")
    fun getCompanies(): LiveData<List<CompanyEntity>>

    @Query("select * from company  order by company desc")
    fun getCompanyByDescending(): LiveData<List<CompanyEntity>>

    @Query("select * from company  order by company asc")
    fun getCompanyByAscending(): LiveData<List<CompanyEntity>>

    @Query("select * from company where company like :name")
    fun getCompanyByName(name: String): LiveData<List<CompanyEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(companies : List<CompanyEntity>)

    @Query("update company set favorite= :favorite where  id = :id")
    fun updateFavorite(id: String,favorite: Boolean )

    @Query("update company set follow= :follow where  id = :id")
    fun updateFollow(id: String,follow: Boolean )
}