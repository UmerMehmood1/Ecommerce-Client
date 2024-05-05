package com.example.ecommerce_client.fragments
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_client.R
import com.example.ecommerce_client.activities.OrderActivity
import com.example.ecommerce_client.activities.ProductActivity
import com.example.ecommerce_client.adapters.ProductAdapter
import com.example.ecommerce_client.databinding.FragmentProductBinding
import com.example.ecommerce_client.models.Product
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class ProductFragment : Fragment(), ProductAdapter.OnItemClickListener {
    private lateinit var binding: FragmentProductBinding
    private val productList = mutableListOf<Product>()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var productAdapter : ProductAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(layoutInflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewProducts.layoutManager = GridLayoutManager(requireContext(),2)
        productAdapter = ProductAdapter(productList, this)
        binding.recyclerViewProducts.adapter = productAdapter
        fetchDataFromFirebase()
        binding.searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                productAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
    private fun fetchDataFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE // Show progress bar
        db.collection("Product")
            .get()
            .addOnSuccessListener { querySnapshot ->
                productList.clear() // Clear existing data
                for (document in querySnapshot.documents) {
                    try{
                        val product = document.toObject(Product::class.java)
                        product?.let { productList.add(it) }

                    }catch(e:Exception){
                        val product = convertToProduct(document)
                        product.let { productList.add(it) }
                    }
                }
                productAdapter.filter("")
                binding.progressBar.visibility = View.GONE // Hide progress bar after data is loaded
            }
            .addOnFailureListener { e ->
                // Handle failure
                binding.progressBar.visibility = View.GONE // Hide progress bar on failure
                // Show error message or handle error as needed
            }
    }
    private fun convertToProduct(document: DocumentSnapshot): Product {
        val id = document.id // Assuming the document ID is the product ID
        val name = document.getString("name") ?: ""
        val description = document.getString("description") ?: ""
        val categoryId = document.getString("categoryId") ?: ""
        val imageUrl = document.getString("imageUrl") ?: ""
        val price = document.getDouble("price") ?: 0.0
        val cost = document.getDouble("cost") ?: 0.0
        val stock = (document.getLong("stock") ?: 0).toInt()
        val weight = document.getDouble("weight") ?: 0.0

        return Product(id, name, description, categoryId, imageUrl, price, cost, stock, weight)
    }
    override fun onItemClick(product: Product) {
        val intent = Intent(context, ProductActivity::class.java)
        intent.putExtra("product",product)
        context?.startActivity(intent)
    }
}
