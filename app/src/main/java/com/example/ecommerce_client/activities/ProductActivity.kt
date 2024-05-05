package com.example.ecommerce_client.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecommerce_client.MyApp
import com.example.ecommerce_client.R
import com.example.ecommerce_client.SharedPreferencesManager
import com.example.ecommerce_client.databinding.ActivityProductBinding
import com.example.ecommerce_client.models.Order
import com.example.ecommerce_client.models.Product
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
        binding.textViewProductPrice.text = "Price: ${product.price}"

        // Load product image using Glide
        Glide.with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.no_image) // Placeholder image while loading
            .error(R.drawable.no_image) // Image to show on error
            .into(binding.imageViewProduct)

        binding.buttonPlaceOrder.setOnClickListener {
            placeOrder()
        }
        binding.addToCart.setOnClickListener {
            addToCart(product)
        }
    }
    private fun addToCart(product: Product){
        val db = MyApp.database
        CoroutineScope(Dispatchers.IO).launch {
            db.cartDao().insert(product)
        }
    }
    private fun placeOrder() {
        val order = Order(
            customerId = SharedPreferencesManager(this).getString("customerId",""), // Replace "customerId" with actual customerId
            productId = product.id.toString(),
            quantity = 1,
            totalPrice = product.price
        )

        // Save order to Firebase
        db.collection("Order")
            .document()
            .set(order)
            .addOnSuccessListener {
                // Handle success
                // For example, show a success message
            }
            .addOnFailureListener { e ->
                // Handle failure
                // For example, show an error message
            }
    }

}