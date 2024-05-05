package com.example.ecommerce_client.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerce_client.R
import com.example.ecommerce_client.databinding.ActivityOrderBinding
import com.example.ecommerce_client.models.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class OrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get order data from intent
        val order = intent.getSerializableExtra("order") as Order

        // Set order details
        binding.textViewOrderId.text = "Order ID: ${order.id}"
        binding.textViewCustomerName.text = "Customer Name: ${order.customerId}"
        binding.textViewProductName.text = "Product Name: ${order.productId}"
        binding.textViewQuantity.text = "Quantity: ${order.quantity}"
        binding.textViewTotalPrice.text = "Total Price: ${order.totalPrice}"
        binding.textViewOrderDate.text = "Order Date: ${formatDate(order.orderDate)}"
    }

    private fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
}