package com.example.ecommerce_client.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_client.MyApp
import com.example.ecommerce_client.adapters.CartAdapter
import com.example.ecommerce_client.databinding.ActivityCartBinding
import com.example.ecommerce_client.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            val cartItems = MyApp.database.cartDao().getAll()
            // Set up RecyclerView
            binding.recyclerViewCart.layoutManager = LinearLayoutManager(this@CartActivity)
            val adapter = CartAdapter(cartItems as ArrayList<Product>)
            binding.recyclerViewCart.adapter = adapter
        }

    }
}
