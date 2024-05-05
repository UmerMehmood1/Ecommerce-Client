package com.example.ecommerce_client.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_client.R
import com.example.ecommerce_client.models.Product

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
        holder.textViewProductName.text = currentItem.name
        holder.textViewProductDescription.text = currentItem.description
        Glide.with(holder.image.context)
            .load(currentItem.imageUrl)
            .placeholder(R.drawable.no_image) // Placeholder image while loading
            .error(R.drawable.no_image) // Image to show on error
            .into(holder.image)
    }

    override fun getItemCount() = filteredProducts.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductDescription: TextView = itemView.findViewById(R.id.textViewProductDescription)
        val image: ImageView = itemView.findViewById(R.id.image)

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
