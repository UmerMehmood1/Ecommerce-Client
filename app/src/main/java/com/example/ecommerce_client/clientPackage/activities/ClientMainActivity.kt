package com.example.ecommerce_client.clientPackage.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ecommerce_client.MyApp
import com.example.ecommerce_client.R
import com.example.ecommerce_client.clientPackage.adapters.FragmentAdapter
import com.example.ecommerce_client.databinding.ActivityMainBinding
import com.example.ecommerce_client.clientPackage.fragments.OrderFragment
import com.example.ecommerce_client.clientPackage.fragments.ProductFragment
import java.util.concurrent.Executors

class ClientMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Executors.newSingleThreadExecutor().execute {
            val count = MyApp.database.cartDao().getTotalItemsCount()
            runOnUiThread {
                binding.numberOfItem.text = count.toString()
            }
        }
        val listOfFragment = ArrayList<Fragment>()
        listOfFragment.add(ProductFragment())
        listOfFragment.add(OrderFragment())
        val adapter = FragmentAdapter(this, listOfFragment)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
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
                    binding.viewPager.currentItem = 0
                }
                "orders" -> {
                    binding.viewPager.currentItem = 1
                }
                "profile" -> {
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Executors.newSingleThreadExecutor().execute {
            val count = MyApp.database.cartDao().getTotalItemsCount()
            runOnUiThread {
                binding.numberOfItem.text = count.toString()
            }
        }
    }
}