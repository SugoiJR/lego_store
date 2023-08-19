package com.storeapp.lego.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.storeapp.lego.data.entity.LegoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface LegoDao {
    @Insert
    fun addLegos(items: List<LegoEntity>)
    @Update
    fun updateLego(items: LegoEntity)
    @Query("SELECT * FROM LegoEntity WHERE uid IN (:legoIds)")
    fun getLegosByIds(legoIds: List<Int>): List<LegoEntity>
    @Query("SELECT * from LegoEntity")
    fun getLegos(): Flow<List<LegoEntity>>
    @Query("SELECT * from LegoEntity WHERE uid == :uid")
    fun findLego(uid: String): Flow<LegoEntity>
    @Query("SELECT COUNT(*) from LegoEntity")
    fun getCountItems(): Int
    @Query("DELETE from LegoEntity")
    fun deleteAll()
}