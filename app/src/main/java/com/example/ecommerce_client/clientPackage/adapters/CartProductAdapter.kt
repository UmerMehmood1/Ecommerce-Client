package com.example.ecommerce_client.clientPackage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_client.MyApp
import com.example.ecommerce_client.R
import com.example.ecommerce_client.databinding.ItemCartProductBinding
import com.example.ecommerce_client.clientPackage.models.Product
import java.util.ArrayList
import java.util.concurrent.Executors

class CartProductAdapter(
    private val products: ArrayList<Product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>() {

    private var filteredProducts: ArrayList<Product> = ArrayList(products)

    interface OnItemClickListener {
        fun onItemClick(product: Product)
        fun onListUpdate(updatedList: ArrayList<Product>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val currentItem = filteredProducts[position]
        holder.binding.textViewProductName.text = currentItem.name
        holder.binding.textViewProductDescription.text = currentItem.description
        Glide.with(holder.binding.image.context)
            .load(currentItem.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.no_image) // Placeholder image while loading
            .error(R.drawable.no_image) // Image to show on error
            .into(holder.binding.image)

        holder.binding.removeFromCartButton.setOnClickListener {
            Executors.newSingleThreadExecutor().execute {
                MyApp.database.cartDao().delete(currentItem)
            }
            if (position != RecyclerView.NO_POSITION) {
                products.remove(currentItem)
                filteredProducts.remove(currentItem)
                listener.onListUpdate(ArrayList(products))
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, filteredProducts.size)
            }
        }
    }

    override fun getItemCount() = filteredProducts.size

    inner class CartProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val binding = ItemCartProductBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(filteredProducts[position])
            }
        }
    }

    fun filter(query: String) {
        filteredProducts = if (query.isEmpty()) {
            ArrayList(products)
        } else {
            ArrayList(products.filter { it.name.contains(query, ignoreCase = true) })
        }
        notifyDataSetChanged()
    }
}
