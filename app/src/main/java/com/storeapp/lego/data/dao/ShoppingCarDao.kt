package com.storeapp.lego.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.storeapp.lego.data.entity.ShoppingCarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCarDao {

    @Query("SELECT * from ShoppingCarEntity WHERE uid == :uid")
    fun findLego(uid: Int): ShoppingCarEntity?

    @Upsert
    fun upSert(item: ShoppingCarEntity)

    @Query("SELECT * from ShoppingCarEntity")
    fun getItemsCart(): Flow<List<ShoppingCarEntity>>

    @Query("DELETE from ShoppingCarEntity")
    fun deleteAll()
}