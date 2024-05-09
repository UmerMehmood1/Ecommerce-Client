package com.example.ecommerce_client.adapters
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_client.FirebaseManager
import com.example.ecommerce_client.R
import com.example.ecommerce_client.activities.ProductActivity
import com.example.ecommerce_client.models.Product

class CategoryWithProductsAdapter(private val categoryWithProductsList: List<FirebaseManager.CategoryWithProducts>) :
    RecyclerView.Adapter<CategoryWithProductsAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryNameTextView: TextView = itemView.findViewById(R.id.textViewCategoryName)
        val productsRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewProducts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryWithProductsList[position]
        holder.categoryNameTextView.text = currentItem.categoryName
        // Set up RecyclerView for products
        holder.productsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ProductAdapter(currentItem.products,object:
                ProductAdapter.OnItemClickListener {
                override fun onItemClick(product: Product) {
                    val intent = Intent(context, ProductActivity::class.java)
                    intent.putExtra("product",product)
                    context?.startActivity(intent)
                }

            })
        }
    }

    override fun getItemCount() = categoryWithProductsList.size
}
