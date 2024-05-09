package com.example.ecommerce_client.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_client.FirebaseManager
import com.example.ecommerce_client.R
import com.example.ecommerce_client.databinding.ItemOrderBinding
import com.example.ecommerce_client.models.Order
import com.example.ecommerce_client.models.Product
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(private val orders: List<Order>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(order: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
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
                holder.binding.textViewProductName.text = "Product: ${product.name}"

            }
        },
            onFailure = {

            })
        holder.binding.textViewOrderId.text = "Order ID: ${currentItem.id}"
        holder.binding.textViewOrderDate.text = "Ordered On: "+formatDate(currentItem.orderDate)
        holder.binding.orderStatus.text = if (currentItem.status == Order.IS_PENDING){
            holder.binding.orderStatus.background = ContextCompat.getDrawable(holder.binding.orderStatus.context, R.drawable.pending_status_bg)
            "Pending"
        } else if (Order.IS_CENCELLED == currentItem.status) {
            holder.binding.orderStatus.background = ContextCompat.getDrawable(holder.binding.orderStatus.context, R.drawable.cancelled_status_bg)
            "Cancelled"
        } else{
            holder.binding.orderStatus.background = ContextCompat.getDrawable(holder.binding.orderStatus.context, R.drawable.delivered_status_bg)
            "Delivered"
        }
    }

    override fun getItemCount() = orders.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
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
