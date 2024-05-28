package com.example.ecommerce_client.clientPackage.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecommerce_client.MyApp
import com.example.ecommerce_client.R
import com.example.ecommerce_client.clientPackage.SharedPreferencesManager
import com.example.ecommerce_client.databinding.ActivityProductBinding
import com.example.ecommerce_client.clientPackage.models.Order
import com.example.ecommerce_client.clientPackage.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var product: Product
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Get product data from intent
        product = intent.getSerializableExtra("product") as Product
        // Set product details
        binding.textViewProductName.text = product.name
        binding.textViewProductDescription.text = product.description
        binding.textViewProductPrice.text = "$${product.price}"

        // Load product image using Glide
        Glide.with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.no_image) // Placeholder image while loading
            .error(R.drawable.no_image) // Image to show on error
            .into(binding.imageViewProduct)

        binding.buttonPlaceOrder.setOnClickListener {
            placeOrder()
            finish()

        }
        binding.addToCart.setOnClickListener {
            addToCart(product)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }
    private fun addToCart(product: Product){
        val db = MyApp.database
        CoroutineScope(Dispatchers.IO).launch {
            db.cartDao().insert(product)
        }
        finish()
    }
    private fun placeOrder() {
        val quantity = binding.spinnerQuantity.selectedItem.toString().toInt()
        Log.d("placeOrder", "placeOrder: $quantity")
        val order = Order(
            customerId = SharedPreferencesManager(this).getString("customerId",""), // Replace "customerId" with actual customerId
            productId = product.id,
            quantity = quantity,
            totalPrice = product.price.toDouble() * quantity
            , status = 0
        )

        // Save order to Firebase
        db.collection("Order")
            .document()
            .set(order)
            .addOnSuccessListener {
                Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show()
                // For example, show an error message
            }
    }

}