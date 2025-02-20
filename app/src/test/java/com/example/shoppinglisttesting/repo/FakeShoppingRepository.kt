package com.example.shoppinglisttesting.repo

import com.example.shoppinglisttesting.data.ImageItem
import com.example.shoppinglisttesting.data.ImageResponse
import com.example.shoppinglisttesting.data.ShoppingItem
import com.example.shoppinglisttesting.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeShoppingRepository : ShoppingRepositoryImpl {

    private val shoppingList = mutableListOf<ShoppingItem>()
    private val observerShoppingList = MutableStateFlow(shoppingList)
    private val observeTotalSum = MutableStateFlow(0)
    private var isNetworkAvailable = true;

    fun setNetwork(state: Boolean) {
        isNetworkAvailable = state
    }

    private fun refreshData() {
        observerShoppingList.value = shoppingList
        observeTotalSum.value = shoppingList.sumOf {
            (it.amount * it.pricePerItem).toInt()
        }
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.add(shoppingItem)
        refreshData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
        refreshData()
    }

    override fun observeShoppingItem(): Flow<List<ShoppingItem>> {
        return observerShoppingList
    }

    override fun getTotalSum(): Flow<Int> {
        return observeTotalSum
    }

    override suspend fun searchForImage(imageQuery: String): Flow<Resource<ImageResponse>> {
        return flow {
            if (isNetworkAvailable) {
                val imagelist = listOf(ImageItem(previewUrl = "Link"))
                emit(Resource.success(ImageResponse(hits = imagelist)))
            } else {
                emit(Resource.error("Network not available", null))
            }
        }
    }
}