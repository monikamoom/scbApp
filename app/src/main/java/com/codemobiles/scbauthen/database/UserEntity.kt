package com.codemobiles.scbauthen.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    //เป็น? เพราะจะให้ gen ไปเรื่อยๆ
    @PrimaryKey (autoGenerate = true)var id: Int?,
    @NonNull var username: String,
    @NonNull var password: String,
    @NonNull var role: Int,
    @ColumnInfo(name = "user_id") var userID: String)

