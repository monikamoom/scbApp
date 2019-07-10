package com.codemobiles.scbauthen.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface CompanyDAO{
    //select all
    @Query("Select * from company")
    fun getCompanies(): List<CompanyEntity>

}