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
import com.example.ecommerce_client.FirebaseManager
import com.example.ecommerce_client.R
import com.example.ecommerce_client.activities.OrderActivity
import com.example.ecommerce_client.activities.ProductActivity
import com.example.ecommerce_client.adapters.CategoryWithProductsAdapter
import com.example.ecommerce_client.adapters.ProductAdapter
import com.example.ecommerce_client.databinding.FragmentProductBinding
import com.example.ecommerce_client.models.Product
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private val productList = mutableListOf<Product>()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var categoryWithProducts : CategoryWithProductsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(layoutInflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
        FirebaseManager().getAllCategoriesWithProducts(onSuccess = {
            categoryWithProducts = CategoryWithProductsAdapter(it.toSet().toList())
            binding.recyclerViewProducts.adapter = categoryWithProducts
        },
            onFailure = {

            })
        binding.searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}
