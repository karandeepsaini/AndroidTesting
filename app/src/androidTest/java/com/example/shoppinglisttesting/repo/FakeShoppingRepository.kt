package com.example.shoppinglisttesting.repo

import com.example.shoppinglisttesting.data.ImageItem
import com.example.shoppinglisttesting.data.ImageResponse
import com.example.shoppinglisttesting.data.ShoppingItem
import com.example.shoppinglisttesting.other.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking


open class FakeShoppingRepository : ShoppingRepository {


    private var isNetworkAvailable = true

    fun setNetwork(state: Boolean) {
        isNetworkAvailable = state
    }

    private val _observerShoppingList = MutableStateFlow<List<ShoppingItem>>(emptyList())
    override fun observeShoppingItem(): Flow<List<ShoppingItem>> = _observerShoppingList

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        println("VALUE IN FAKE REPO : " + Gson().toJson(shoppingItem))
        _observerShoppingList.update { _observerShoppingList.value + shoppingItem}
        println("LIST IN FAKE REPO : " + Gson().toJson(_observerShoppingList.value))
    }


    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        _observerShoppingList.value =
            _observerShoppingList.value.filterNot { it.uid == shoppingItem.uid }
    }


    override fun getTotalSum(): Flow<Int> {
        return flow {
            _observerShoppingList.value.sumOf {
                (it.amount * it.pricePerItem)
            }
        }
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