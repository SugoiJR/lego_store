package com.storeapp.lego.data.di

import android.content.Context
import androidx.room.Room
import com.storeapp.lego.data.LegoDataBase
import com.storeapp.lego.data.dao.LegoDao
import com.storeapp.lego.data.dao.ShoppingCarDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideTodoDB(@ApplicationContext appContext: Context): LegoDataBase {
        return Room.databaseBuilder(
            appContext,
            LegoDataBase::class.java,
            "lego_db"
        ).build()
    }

    @Provides
    fun provideLegoDao(legoDb: LegoDataBase): LegoDao {
        return legoDb.legoDao()
    }
    @Provides
    fun provideShoppingCarDao(legoDb: LegoDataBase): ShoppingCarDao {
        return legoDb.shoppingCarDao()
    }
}