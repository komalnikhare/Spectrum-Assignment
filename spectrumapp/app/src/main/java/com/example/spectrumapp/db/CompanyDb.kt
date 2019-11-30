package com.example.spectrumapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spectrumapp.db.dao.CompanyDao
import com.example.spectrumapp.db.dao.MemberDao
import com.example.spectrumapp.db.entities.CompanyEntity
import com.example.spectrumapp.db.entities.MemberEntity

@Database(entities = [CompanyEntity::class, MemberEntity::class], version = 1, exportSchema = false)
abstract class CompanyDb: RoomDatabase() {

    companion object{
        private var INSTANCE : CompanyDb? = null

        fun getDb(context: Context): CompanyDb{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    CompanyDb::class.java, "company_db")
                    .allowMainThreadQueries().build()
            }
            return INSTANCE as CompanyDb
        }
    }

    abstract fun daoCompany(): CompanyDao
    abstract fun daoMembers(): MemberDao
}