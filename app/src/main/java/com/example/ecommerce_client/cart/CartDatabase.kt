package com.example.ecommerce_client.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerce_client.models.Product

@Database(entities = [Product::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
