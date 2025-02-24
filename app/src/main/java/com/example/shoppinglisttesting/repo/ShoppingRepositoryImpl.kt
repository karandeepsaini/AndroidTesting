package com.example.shoppinglisttesting.repo

import com.example.shoppinglisttesting.network.ApiService
import com.example.shoppinglisttesting.data.ImageResponse
import com.example.shoppinglisttesting.data.ShoppingItem
import com.example.shoppinglisttesting.db.Dao
import com.example.shoppinglisttesting.other.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ShoppingRepositoryImpl @Inject constructor(
    private val dao: Dao,
    private val service: ApiService
) : ShoppingRepository {


    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insert(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.delete(shoppingItem)
    }

    override fun observeShoppingItem(): Flow<List<ShoppingItem>> {
        return dao.getAll()
    }

    override fun getTotalSum(): Flow<Int> {
        return dao.getTotalPrice().map { it ?: 0 }
    }

    override suspend fun searchForImage(imageQuery: String): Flow<Resource<ImageResponse>> {
        return flow {
            emit(Resource.loading(null))
            try {
                val response = service.getImages(image = imageQuery)
                emit(Resource.success(response))
            } catch (e: Exception) {
                emit(Resource.error(e.message ?: "Failed to load image", null))
            }
        }.flowOn(Dispatchers.IO)
    }
}