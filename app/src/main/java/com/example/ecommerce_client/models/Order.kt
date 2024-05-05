package com.example.ecommerce_client.models

import java.io.Serializable

data class Order(
    val id: String = System.currentTimeMillis().toString(),
    val customerId: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val orderDate: Long = System.currentTimeMillis(),
    val status: Int
): Serializable{
    constructor() : this("", "", "", 1, 0.0, 1231534, 0)

    companion object{
        val IS_PENDING = 0
        val IS_CENCELLED = 1
        val IS_DELIVERED = 2
    }
}
