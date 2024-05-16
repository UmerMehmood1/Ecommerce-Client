package com.example.ecommerce_client.adminPackage.models

import java.io.Serializable

data class Admin(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val address: String = ""
): Serializable