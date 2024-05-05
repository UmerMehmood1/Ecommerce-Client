package com.example.ecommerce_client.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_client.R
import com.example.ecommerce_client.activities.ProductActivity
import com.example.ecommerce_client.models.Product

class CartAdapter(private val cartItems: ArrayList<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.textViewCartItemName)
        val itemDescriptionTextView: TextView = itemView.findViewById(R.id.textViewCartItemDescription)
        val itemPriceTextView: TextView = itemView.findViewById(R.id.textViewCartItemPrice)
        val itemStockTextView: TextView = itemView.findViewById(R.id.textViewCartItemStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]
        holder.itemNameTextView.text = currentItem.name
        holder.itemDescriptionTextView.text = currentItem.description
        holder.itemPriceTextView.text = currentItem.price.toString()
        holder.itemStockTextView.text = currentItem.stock.toString()
        holder.itemView.rootView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductActivity::class.java)
            intent.putExtra("product",currentItem)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = cartItems.size

    // Method to add a new cart item
    fun addItem(item: Product) {
        cartItems.add(item)
        notifyItemInserted(cartItems.size - 1)
    }

    // Method to update an existing cart item
    fun updateItem(item: Product) {
        val index = cartItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            cartItems[index] = item
            notifyItemChanged(index)
        }
    }

    // Method to remove a cart item
    fun removeItem(item: Product) {
        val index = cartItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
