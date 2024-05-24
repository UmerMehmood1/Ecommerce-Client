package com.example.ecommerce_client.clientPackage.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerce_client.clientPackage.models.Product

@Database(entities = [Product::class], version = 2)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
