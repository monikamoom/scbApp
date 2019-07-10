package com.codemobiles.scbauthen.database

import androidx.room.*


@Dao
interface UserDAO{
    //select all
    @Query("Select * from users")
    fun getUsers(): List<UserEntity>

    //select by where
    @Query("Select * from users where username = :username")
    fun getUser(username: String): UserEntity?

    @Insert
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Update
    fun update(user: UserEntity)
}