package com.susan.usergithub.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query(value = "SELECT * from favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query(value = "SELECT count(*) from favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query(value = "DELETE from favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}