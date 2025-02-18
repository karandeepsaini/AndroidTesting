package com.example.shoppinglisttesting.network

import com.example.shoppinglisttesting.data.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

//https://pixabay.com/api/?key=48820846-7a792f4549b29df8549fd6624&q=yellow+flowers&image_type=photo
interface ApiService {

    @GET("/api")
    suspend fun getImages(
        @Query("key") apiKey: String = "48820846-7a792f4549b29df8549fd6624",
        @Query("q") image: String,
        @Query("image_type") imageType: String = "photo"
    ): ImageResponse
}