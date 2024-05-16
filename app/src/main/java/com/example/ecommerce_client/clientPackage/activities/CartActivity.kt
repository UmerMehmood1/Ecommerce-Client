package com.example.ecommerce_client.clientPackage.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce_client.MyApp
import com.example.ecommerce_client.R
import com.example.ecommerce_client.clientPackage.adapters.CartProductAdapter
import com.example.ecommerce_client.databinding.ActivityCartBinding
import com.example.ecommerce_client.clientPackage.fragments.EmptyListFragment
import com.example.ecommerce_client.clientPackage.models.Product
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
                binding.progress.visibility = GONE
                if (cartItems.isEmpty()) {
                    binding.recyclerViewCart.visibility = View.GONE
                    binding.fragmentContainerCart.visibility = View.VISIBLE
                    val emptyListFragment = EmptyListFragment()
                    emptyListFragment.setActionText("Cart is empty at the moment. Go Shop and add favourite items to Cart.")
                    replaceFragment(
                        emptyListFragment
                    )
                } else {
                    binding.recyclerViewCart.visibility = View.VISIBLE
                    binding.fragmentContainerCart.visibility = View.GONE
                    binding.recyclerViewCart.layoutManager = GridLayoutManager(this@CartActivity,2)
                    val adapter = CartProductAdapter(cartItems as ArrayList<Product>, object : CartProductAdapter.OnItemClickListener{
                        override fun onItemClick(product: Product) {
                            val intent = Intent(this@CartActivity, ProductActivity::class.java)
                            intent.putExtra("product",product)
                            startActivity(intent)
                        }

                        override fun onListUpdate(updatedList: java.util.ArrayList<Product>) {
                            if (updatedList.isEmpty()) {
                                binding.recyclerViewCart.visibility = GONE
                                binding.fragmentContainerCart.visibility = View.VISIBLE
                                val emptyListFragment = EmptyListFragment()
                                emptyListFragment.setActionText("Cart is empty at the moment. Go Shop and add favourite items to Cart.")
                                replaceFragment(
                                    emptyListFragment
                                )
                            }
                        }
                    })
                    binding.recyclerViewCart.adapter = adapter
                }
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerCart, fragment)
        transaction.commit()
    }

}
