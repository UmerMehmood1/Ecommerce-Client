package com.example.ecommerce_client.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_client.MyApp
import com.example.ecommerce_client.adapters.ProductAdapter
import com.example.ecommerce_client.databinding.ActivityCartBinding
import com.example.ecommerce_client.models.Product
import java.util.concurrent.Executors

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private var cartItems = ArrayList<Product>() as List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Executors.newSingleThreadExecutor().execute {
            cartItems = MyApp.database.cartDao().getAll()
            runOnUiThread {
                binding.recyclerViewCart.layoutManager = GridLayoutManager(this@CartActivity,2)
                val adapter = ProductAdapter(cartItems as ArrayList<Product>, object : ProductAdapter.OnItemClickListener{
                    override fun onItemClick(product: Product) {
                        val intent = Intent(this@CartActivity, ProductActivity::class.java)
                        intent.putExtra("product",product)
                        startActivity(intent)
                    }

                })
                binding.recyclerViewCart.adapter = adapter
            }
        }
    }

}
