package com.example.shoppinglisttesting.ui.repo

import com.example.shoppinglisttesting.network.ApiService
import com.example.shoppinglisttesting.data.ImageResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class ShoppingRepository @Inject constructor(
    private val service: ApiService
) {
    suspend fun getImage(search: String): Flow<ImageResponse> {
        return flow {
            val response = service.getImages(
                image = search
            )
            delay(1000L)
            emit(response)
        }
    }
}