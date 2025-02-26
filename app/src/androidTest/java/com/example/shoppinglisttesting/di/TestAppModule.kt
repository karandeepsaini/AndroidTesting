package com.example.shoppinglisttesting.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.shoppinglisttesting.db.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideDatabaseInMemoryDb(@ApplicationContext context: Context) : ShoppingDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            ShoppingDatabase::class.java
        ).allowMainThreadQueries().build()
    }

}