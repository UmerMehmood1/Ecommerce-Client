package com.example.ecommerce_client.models

import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val categoryId: String,
    val imageUrl: String,
    val price: Double,
    val cost: Double,
    val stock: Int,
    val weight: Double
) : Serializable {
    constructor() : this("", "", "", "", "", 0.0, 0.0, 0, 0.0)
}