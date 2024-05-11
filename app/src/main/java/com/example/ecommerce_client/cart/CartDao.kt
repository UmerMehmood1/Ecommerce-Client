package com.example.ecommerce_client.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce_client.models.Product

@Dao
interface CartDao {
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>
    @Query("SELECT COUNT(id) FROM products")
    fun getTotalItemsCount(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)
    @Delete
    fun delete(product: Product)
}
