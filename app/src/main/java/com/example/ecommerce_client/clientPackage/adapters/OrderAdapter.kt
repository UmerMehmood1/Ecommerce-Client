package com.example.ecommerce_client.clientPackage.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_client.FirebaseManager
import com.example.ecommerce_client.R
import com.example.ecommerce_client.databinding.ItemOrderBinding
import com.example.ecommerce_client.clientPackage.models.Order
import com.example.ecommerce_client.clientPackage.models.Product
import com.example.ecommerce_client.clientPackage.swipeHelpers.OrderItemSwipeHelper
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(
    private val orders: ArrayList<Order>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(order: Order)
        fun onItemSwiped()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orders[position]
        FirebaseManager().getAllProducts(onSuccess = {
            var product: Product? = null
            for (item in it) {
                if (item.id == currentItem.productId) {
                    product = item
                }
            }
            if (product != null) {
                Glide.with(holder.binding.productImage.context)
                    .load(product.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.no_image) // Placeholder image while loading
                    .error(R.drawable.no_image) // Image to show on error
                    .into(holder.binding.productImage)
                holder.binding.textViewProductName.text = product.name

            }
        },onFailure = {

            })
        holder.binding.textViewOrderId.text = currentItem.id
        holder.binding.textViewOrderDate.text = formatDate(currentItem.orderDate)
        holder.binding.orderStatus.text = if (currentItem.status == Order.IS_PENDING) {
            holder.binding.orderStatus.setTextColor(
                ContextCompat.getColor(
                    holder.binding.orderStatus.context,
                    R.color.orange
                )
            )
            "Pending"
        }
        else if (Order.IS_CENCELLED == currentItem.status) {
            holder.binding.orderStatus.setTextColor(Color.parseColor("#F80000"))
            "Cancelled"
        }
        else {
            holder.binding.orderStatus.setTextColor(Color.parseColor("#4CAF50"))
            "Delivered"
        }
        holder.binding.totalOrderPrice.text = "$${currentItem.totalPrice}"
        holder.binding.quantity.text = currentItem.quantity.toString()
    }

    fun attachSwipeHelper(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : OrderItemSwipeHelper(
            ContextCompat.getDrawable(
                recyclerView.context,
                R.drawable.baseline_delete_24
            )!!
        ) {

            override fun onSwipe(position: Int) {
                Log.d("onSwipe", "onSwipe: $position")
                val swipedOrder = orders[position]
                if (position != RecyclerView.NO_POSITION) {
                    FirebaseManager().deleteOrderByOrderId(swipedOrder.id, onSuccess = {
                        orders.removeAt(position)
                        notifyItemRemoved(position)
                        listener.onItemSwiped()
                        Toast.makeText(
                            recyclerView.context,
                            "Order is cancelled successfully with id ${swipedOrder.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, onFailure = {
                        listener.onItemSwiped()
                        Toast.makeText(
                            recyclerView.context,
                            "Failed to cancel order with id ${swipedOrder.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun getItemCount() = orders.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var binding = ItemOrderBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(orders[position])
            }
        }
    }

    private fun formatDate(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        return dateFormat.format(date)
    }
}
