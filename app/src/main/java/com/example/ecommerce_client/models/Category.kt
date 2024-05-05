package com.example.ecommerce_client.models

import java.io.Serializable

data class Category(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val position: Int = 0
): Serializable