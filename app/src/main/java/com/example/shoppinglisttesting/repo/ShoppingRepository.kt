package com.example.shoppinglisttesting.repo

import com.example.shoppinglisttesting.data.ImageResponse
import com.example.shoppinglisttesting.data.ShoppingItem
import com.example.shoppinglisttesting.other.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeShoppingItem() : Flow<List<ShoppingItem>>
    fun getTotalSum() : Flow<Int>
    suspend fun searchForImage(imageQuery : String) : Flow<Resource<ImageResponse>>
}