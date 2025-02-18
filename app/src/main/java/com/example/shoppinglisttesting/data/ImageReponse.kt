package com.example.shoppinglisttesting.data

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageItem>
)

data class ImageItem(
    val id: Int,
    @SerializedName("pageURL") val pageUrl: String,
    val type: String,
    val tags: String,
    @SerializedName("previewURL") val previewUrl: String,
    val previewWidth: Int,
    val previewHeight: Int,
    @SerializedName("webformatURL") val webformatUrl: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    @SerializedName("largeImageURL") val largeImageUrl: String,
    @SerializedName("fullHDURL") val fullHdUrl: String?,
    @SerializedName("imageURL") val imageUrl: String?,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    @SerializedName("user_id") val userId: Int,
    val user: String,
    @SerializedName("userImageURL") val userImageUrl: String
)

