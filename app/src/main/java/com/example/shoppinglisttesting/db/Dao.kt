package com.example.shoppinglisttesting.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglisttesting.data.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingItem: ShoppingItem)

    @Delete
    fun delete(shoppingItem: ShoppingItem)

    @Query("select SUM(price_per_item * amount) from shopping_items")
    fun getTotalPrice() : Flow<Int?>

    @Query("select * from shopping_items ORDER BY uid Desc")
    fun getAll() : Flow<List<ShoppingItem>>

}