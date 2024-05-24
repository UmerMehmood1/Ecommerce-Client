package com.example.ecommerce_client.clientPackage.models

import java.io.Serializable

data class Category(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val position: String = ""
): Serializable