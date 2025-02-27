package com.example.shoppinglisttesting.data

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    val total: Int? =null,
    val totalHits: Int? = null,
    val hits: List<ImageItem>
)

data class ImageItem(
    val id: Int? = null,
    @SerializedName("pageURL") val pageUrl: String? = null,
    val type: String? = null,
    val tags: List<ImageItem>? = null,
    @SerializedName("previewURL") val previewUrl: String,
    val previewWidth: Int? = null,
    val previewHeight: Int? = null,
    @SerializedName("webformatURL") val webformatUrl: String? = null,
    val webformatWidth: Int? = null,
    val webformatHeight: Int? = null,
    @SerializedName("largeImageURL") val largeImageUrl: String? = null,
    @SerializedName("fullHDURL") val fullHdUrl: String? = null,
    @SerializedName("imageURL") val imageUrl: String? = null,
    val imageWidth: Int? = null,
    val imageHeight: Int? = null,
    val imageSize: Int? = null,
    val views: Int? = null,
    val downloads: Int? = null,
    val likes: Int? = null,
    val comments: Int? = null,
    @SerializedName("user_id") val userId: Int? = null,
    val user: String? = null,
    @SerializedName("userImageURL") val userImageUrl: String? = null
)

