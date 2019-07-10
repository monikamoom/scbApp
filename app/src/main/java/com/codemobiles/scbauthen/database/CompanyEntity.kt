package com.codemobiles.scbauthen.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class CompanyEntity(
    //เป็น? เพราะจะให้ gen ไปเรื่อยๆ
    @PrimaryKey (autoGenerate = true)var id: Int?,
    @NonNull var name: String,
    @ColumnInfo(name = "company_id") var companyID: String)

