package com.example.shoppinglisttesting.repo

import com.example.shoppinglisttesting.db.Dao
import com.example.shoppinglisttesting.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideShoppingRepository(
        dao : Dao,service :ApiService
    ) = ShoppingRepositoryImpl(dao,service) as ShoppingRepository

    @Provides
    @Singleton
    fun provideDispatcher() :  CoroutineDispatcher{
        return Dispatchers.IO
    }
}