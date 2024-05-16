package com.example.ecommerce_client.clientPackage.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_client.R
import com.example.ecommerce_client.databinding.ItemProductBinding
import com.example.ecommerce_client.clientPackage.models.Product

class ProductAdapter(private val products: List<Product>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var filteredProducts: List<Product> = products

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = filteredProducts[position]
        holder.binding.textViewProductName.text = currentItem.name
        holder.binding.textViewProductDescription.text = currentItem.description
        Glide.with(holder.binding.image.context)
            .load(currentItem.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.no_image) // Placeholder image while loading
            .error(R.drawable.no_image) // Image to show on error
            .into(holder.binding.image)
    }

    override fun getItemCount() = filteredProducts.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val binding = ItemProductBinding.bind(itemView)
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
            products
        } else {
            products.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}
