package com.example.shoppinglisttesting.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val uid : Int,
    @ColumnInfo(name = "image")var image: String = "",
    @ColumnInfo(name = "name")var name: String = "",
    @ColumnInfo(name = "amount")var amount: Int = 0,
    @ColumnInfo(name = "price_per_item")var pricePerItem: Double = 0.0
)
