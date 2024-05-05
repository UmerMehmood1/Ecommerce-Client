package com.example.ecommerce_client.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_client.R
import com.example.ecommerce_client.models.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(private val orders: List<Order>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(order: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orders[position]
        holder.textViewOrderId.text = "Order ID: ${currentItem.id}"
        holder.textViewCustomerName.text = "Customer: ${currentItem.customerId}"
        holder.textViewProductName.text = "Product: ${currentItem.productId}"
        holder.textViewOrderDate.text = formatDate(currentItem.orderDate)
    }

    override fun getItemCount() = orders.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewOrderId: TextView = itemView.findViewById(R.id.textViewOrderId)
        val textViewCustomerName: TextView = itemView.findViewById(R.id.textViewCustomerName)
        val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewOrderDate: TextView = itemView.findViewById(R.id.textViewOrderDate)

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
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
}
