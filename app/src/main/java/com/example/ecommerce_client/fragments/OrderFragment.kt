package com.example.ecommerce_client.fragments

import android.content.Intent
import android.os.Bundle
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
import com.example.ecommerce_client.models.Order
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class OrderFragment : Fragment(), OrderAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private val orderList = mutableListOf<Order>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
        orderAdapter = OrderAdapter(orderList as ArrayList<Order>, this)
        recyclerView.adapter = orderAdapter
        orderAdapter.attachSwipeHelper(recyclerView)
        fetchOrdersFromFirebase()

        return view
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
                        if (order.customerId == SharedPreferencesManager(requireContext()).getString("customerId","")){
                            orderList.add(it)
                        }
                    }
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
        intent.putExtra("order",order)
        context?.startActivity(intent)
    }
}