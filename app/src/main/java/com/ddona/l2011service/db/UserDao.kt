package com.ddona.l2011service.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ddona.l2011service.db.entity.User

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<User>>
}