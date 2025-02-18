package com.example.shoppinglisttesting.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppinglisttesting.data.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase(){
    abstract fun shoppingDao() : Dao
}