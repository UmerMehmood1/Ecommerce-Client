package com.example.ecommerce_client.clientPackage.models

import java.io.Serializable

data class Customer(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val address: String = ""
): Serializable