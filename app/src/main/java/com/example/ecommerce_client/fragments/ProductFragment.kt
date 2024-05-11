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
            binding.progress.visibility = View.GONE
            if (it.isEmpty()) {
                binding.recyclerViewProducts.visibility = View.INVISIBLE
                binding.fragmentContainerProduct.visibility = View.VISIBLE
                val emptyListFragment = EmptyListFragment()
                emptyListFragment.setActionText("No Products. Products are coming soon")
                replaceFragment(
                    emptyListFragment
                )
                replaceFragment(EmptyListFragment())
            } else {
                binding.recyclerViewProducts.visibility = View.VISIBLE
                binding.fragmentContainerProduct.visibility = View.INVISIBLE
            }
        },
            onFailure = {

            })
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerProduct, fragment)
        transaction.commit()
    }

}
