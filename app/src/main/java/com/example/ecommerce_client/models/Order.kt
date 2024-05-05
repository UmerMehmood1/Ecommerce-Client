package com.example.ecommerce_client.models

import java.io.Serializable

data class Order(
    val id: String = System.currentTimeMillis().toString(),
    val customerId: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val orderDate: Long = System.currentTimeMillis()
): Serializable
