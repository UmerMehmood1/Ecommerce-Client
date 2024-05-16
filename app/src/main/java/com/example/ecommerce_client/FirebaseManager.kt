package com.example.ecommerce_client

import android.util.Log
import com.example.ecommerce_client.adminPackage.models.Admin
import com.example.ecommerce_client.clientPackage.models.Category
import com.example.ecommerce_client.clientPackage.models.Customer
import com.example.ecommerce_client.clientPackage.models.Order
import com.example.ecommerce_client.clientPackage.models.Product
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager {
    data class CategoryWithProducts(
        val categoryName: String,
        val products: List<Product>
    )
    private val db = FirebaseFirestore.getInstance()

    // Function to fetch all categories and products
    fun getAllCategoriesWithProducts(onSuccess: (List<CategoryWithProducts>) -> Unit, onFailure: (Exception) -> Unit) {
        getAllCategories({ categories ->
            val categoryWithProductsList = mutableListOf<CategoryWithProducts>()
            val categoryCount = categories.size
            var categoriesProcessed = 0

            categories.forEach { category ->
                getAllProductsByCategory(category.id,
                    onSuccess = { products ->
                        Log.d("FirebaseManager", "getAllCategoriesWithProducts: "+category.id)
                        Log.d("FirebaseManager", "getAllCategoriesWithProducts: "+products)
                        val categoryWithProducts = CategoryWithProducts(category.title, products)
                        categoryWithProductsList.add(categoryWithProducts)
                        categoriesProcessed++
                        if (categoriesProcessed == categoryCount) {
                            onSuccess(categoryWithProductsList)
                        }
                    },
                    onFailure = { exception ->
                        onFailure(exception)
                    }
                )
            }
        }, { exception ->
            onFailure(exception)
        })
    }
    // CRUD operations for Product collection
    fun getAllProductsByCategory(categoryId: String, onSuccess: (List<Product>) -> Unit, onFailure: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        val products = mutableListOf<Product>()

        db.collection("Product")
            .whereEqualTo("categoryId", categoryId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val product = document.toObject(Product::class.java)
                        products.add(product)
                    }
                    onSuccess(products)
                } else {
                    onFailure(task.exception!!)
                }
            }
    }
    fun addProduct(product: Product, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Product")
            .add(product)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun updateProduct(product: Product, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Product")
            .document(product.id.toString())
            .set(product)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun deleteProduct(productId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Product")
            .document(productId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    // CRUD operations for Category collection
    fun addCategory(category: Category, onSuccess: (category: Category) -> Unit, onFailure: () -> Unit) {
        db.collection("Category")
            .add(category)
            .addOnSuccessListener { onSuccess(category) }
            .addOnFailureListener { onFailure() }
    }
    fun updateCategory(category: Category, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Category")
            .document(category.id)
            .set(category)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun deleteCategory(categoryId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Category")
            .document(categoryId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }


    // CRUD operations for Customer collection
    fun addCustomer(customer: Customer, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Customer")
            .add(customer)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun updateCustomer(customer: Customer, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Customer")
            .document(customer.id)
            .set(customer)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun deleteCustomer(customerId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Customer")
            .document(customerId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }


    // CRUD operations for Admin collection
    fun addAdmin(admin: Admin, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Admin")
            .add(admin)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun updateAdmin(customer: Admin, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Admin")
            .document(customer.id)
            .set(customer)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun deleteAdmin(adminId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Admin")
            .document(adminId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }


    fun addOrder(order: Order, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Order")
            .add(order)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    fun updateOrder(order: Order, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Order")
            .document(order.id)
            .set(order)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    fun deleteOrderByOrderId(orderId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Order")
            .whereEqualTo("id", orderId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Iterate through the query snapshot to delete each document
                val deleteTasks = mutableListOf<com.google.android.gms.tasks.Task<Void>>()
                for (document in querySnapshot.documents) {
                    val deleteTask = db.collection("Order").document(document.id).delete()
                    deleteTasks.add(deleteTask)
                }

                // Wait for all delete tasks to complete
                com.google.android.gms.tasks.Tasks.whenAllComplete(deleteTasks)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure(e as Exception)
                    }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
    fun deleteOrder(orderId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Order")
            .document(orderId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }
    // Retrieve a single product by its ID
    fun getProduct(productId: String, onSuccess: (Product?) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Product")
            .document(productId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val product = documentSnapshot.toObject(Product::class.java)
                onSuccess(product)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Retrieve all products
    fun getAllProducts(onSuccess: (List<Product>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Product")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val products = mutableListOf<Product>()
                for (document in querySnapshot.documents) {
                    val product = document.toObject(Product::class.java)
                    product?.let { products.add(it) }
                }
                onSuccess(products)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Retrieve a single category by its ID
    fun getCategory(categoryId: String, onSuccess: (Category?) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Category")
            .document(categoryId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val category = documentSnapshot.toObject(Category::class.java)
                onSuccess(category)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Retrieve all categories
    fun getAllCategories(onSuccess: (List<Category>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Category")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val categories = mutableListOf<Category>()
                for (document in querySnapshot.documents) {
                    val category = document.toObject(Category::class.java)
                    category?.let { categories.add(it) }
                }
                onSuccess(categories)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Retrieve a single customer by their ID
    fun getCustomer(customerId: String, onSuccess: (Customer?) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Customer")
            .document(customerId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val customer = documentSnapshot.toObject(Customer::class.java)
                onSuccess(customer)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Retrieve all customers
    fun getAllCustomers(onSuccess: (List<Customer>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Customer")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val customers = mutableListOf<Customer>()
                for (document in querySnapshot.documents) {
                    val customer = document.toObject(Customer::class.java)
                    customer?.let { customers.add(it) }
                }
                onSuccess(customers)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Retrieve a single order by its ID
    fun getOrder(orderId: String, onSuccess: (Order?) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Order")
            .document(orderId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val order = documentSnapshot.toObject(Order::class.java)
                onSuccess(order)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Retrieve all orders
    fun getAllOrders(onSuccess: (List<Order>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Order")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val orders = mutableListOf<Order>()
                for (document in querySnapshot.documents) {
                    val order = document.toObject(Order::class.java)
                    order?.let { orders.add(it) }
                }
                onSuccess(orders)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
