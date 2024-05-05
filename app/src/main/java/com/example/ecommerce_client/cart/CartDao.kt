package com.example.ecommerce_client.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ecommerce_client.models.Product

@Dao
interface CartDao {
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>
    @Insert
    suspend fun insert(product: Product)
    @Insert
    suspend fun insertAll(products: List<Product>)
}
