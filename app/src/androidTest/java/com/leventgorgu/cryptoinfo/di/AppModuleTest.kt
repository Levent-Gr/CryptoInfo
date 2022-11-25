package com.leventgorgu.cryptoinfo.di

import android.content.Context
import androidx.room.Room
import com.leventgorgu.cryptoinfo.roomdb.CryptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context :Context) =
        Room.inMemoryDatabaseBuilder(context,CryptoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}