package com.example.ecommerce_client.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ecommerce_client.R
import com.example.ecommerce_client.databinding.ActivityMainBinding
import com.example.ecommerce_client.fragments.OrderFragment
import com.example.ecommerce_client.fragments.ProductFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.cart.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.shopHomeExpandableBottomBar.onItemSelectedListener = { _, menuItem, _ ->
            when (menuItem.text.toString().lowercase()) {
                "home" -> {
                    replaceFragment(ProductFragment())
                }
                "orders" -> {
                    replaceFragment(OrderFragment())
                }
                "profile" -> {
                }
            }
        }
        replaceFragment(ProductFragment())
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameForFragment, fragment)
            .commit()
    }
}