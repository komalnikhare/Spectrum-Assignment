package com.example.spectrumapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spectrumapp.db.entities.CompanyEntity
import com.example.spectrumapp.db.entities.MemberEntity

@Dao
interface MemberDao {

    @Query("Select * from member where companyId = :companyId")
    fun getMembers(companyId : String): LiveData<List<MemberEntity>>

    @Query("Select * from member")
    fun getAllMembers(): LiveData<List<MemberEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(members: List<MemberEntity>)


    @Query("update member set favorite= :favorite where  id = :id")
    fun updateFavorite(id: String,favorite: Boolean )

    @Query("select * from member where name like :name")
    fun getMemberByName(name: String): LiveData<List<MemberEntity>>

    @Query("select * from member where companyId = :id and name like :name")
    fun getMemberByNameForCompany(id: String,name: String): LiveData<List<MemberEntity>>


    @Query("select * from member order by name desc")
    fun getMemberByNameDesc(): LiveData<List<MemberEntity>>

    @Query("select * from member  order by name asc")
    fun getMemberByNameAsc(): LiveData<List<MemberEntity>>

    @Query("select * from member where companyId= :companyId order by name desc")
    fun getMemberByNameDesc(companyId: String): LiveData<List<MemberEntity>>

    @Query("select * from member where companyId= :companyId order by name asc")
    fun getMemberByNameAsc(companyId: String): LiveData<List<MemberEntity>>


    @Query("select * from member  order by age desc")
    fun getMemberByAgeDesc(): LiveData<List<MemberEntity>>

    @Query("select * from member  order by age asc")
    fun getMemberByAgeAsc(): LiveData<List<MemberEntity>>

    @Query("select * from member where companyId= :companyId order by age desc")
    fun getMemberByAgeDesc(companyId: String): LiveData<List<MemberEntity>>

    @Query("select * from member where companyId= :companyId order by age asc")
    fun getMemberByAgeAsc(companyId: String): LiveData<List<MemberEntity>>

}