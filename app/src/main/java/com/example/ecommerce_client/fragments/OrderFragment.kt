package com.example.ecommerce_client.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_client.R
import com.example.ecommerce_client.SharedPreferencesManager
import com.example.ecommerce_client.activities.OrderActivity
import com.example.ecommerce_client.adapters.OrderAdapter
import com.example.ecommerce_client.databinding.FragmentOrderBinding
import com.example.ecommerce_client.models.Order
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class OrderFragment : Fragment(), OrderAdapter.OnItemClickListener {

    private lateinit var orderAdapter: OrderAdapter
    private val orderList = mutableListOf<Order>()
    private val db = FirebaseFirestore.getInstance()
    lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        binding.recyclerViewOrders.layoutManager = GridLayoutManager(requireContext(), 1)
        orderAdapter = OrderAdapter(orderList as ArrayList<Order>, this)
        binding.recyclerViewOrders.adapter = orderAdapter
        orderAdapter.attachSwipeHelper(binding.recyclerViewOrders)
        fetchOrdersFromFirebase()

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerOrder, fragment)
        transaction.commit()
    }

    private fun fetchOrdersFromFirebase() {
        // Fetch orders from Firebase Firestore
        db.collection("Order")
            .get()
            .addOnSuccessListener { querySnapshot ->
                orderList.clear() // Clear existing data
                for (document in querySnapshot.documents) {
                    val order = document.toObject(Order::class.java)
                    order?.let {
                        if (order.customerId == SharedPreferencesManager(requireContext()).getString(
                                "customerId",
                                ""
                            )
                        ) {
                            orderList.add(it)
                        }
                    }
                }
                binding.progress.visibility = View.GONE
                if (orderList.isEmpty()) {
                    binding.recyclerViewOrders.visibility = View.GONE
                    binding.fragmentContainerOrder.visibility = View.VISIBLE
                    val emptyListFragment = EmptyListFragment()
                    emptyListFragment.setActionText("Nothing here. Order something to get started")
                    replaceFragment(
                        emptyListFragment
                    )
                    replaceFragment(emptyListFragment)
                } else {
                    binding.recyclerViewOrders.visibility = View.VISIBLE
                    binding.fragmentContainerOrder.visibility = View.GONE
                }
                orderAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Handle failure
                // For example, show an error message
            }
    }

    override fun onItemClick(order: Order) {
        val intent = Intent(context, OrderActivity::class.java)
        intent.putExtra("order", order)
        context?.startActivity(intent)
    }

    override fun onItemSwiped() {
        if (orderAdapter.itemCount == 0) {
            binding.recyclerViewOrders.visibility = View.GONE
            binding.fragmentContainerOrder.visibility = View.VISIBLE
            val emptyListFragment = EmptyListFragment()
            emptyListFragment.setActionText("Nothing here. Order something to get started")
            replaceFragment(
                emptyListFragment
            )
            replaceFragment(emptyListFragment)
        } else {
            binding.recyclerViewOrders.visibility = View.VISIBLE
            binding.fragmentContainerOrder.visibility = View.GONE
        }
    }
}