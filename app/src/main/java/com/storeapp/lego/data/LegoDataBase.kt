package com.storeapp.lego.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.storeapp.lego.data.dao.LegoDao
import com.storeapp.lego.data.dao.ShoppingCarDao
import com.storeapp.lego.data.entity.LegoEntity
import com.storeapp.lego.data.entity.ShoppingCarEntity


@Database(
    entities = [
        LegoEntity::class,
        ShoppingCarEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LegoDataBase : RoomDatabase() {
    abstract fun shoppingCarDao(): ShoppingCarDao
    abstract fun legoDao(): LegoDao
}