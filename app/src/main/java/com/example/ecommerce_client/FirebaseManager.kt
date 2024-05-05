package com.example.ecommerce_client

import com.example.ecommerce_client.models.Category
import com.example.ecommerce_client.models.Customer
import com.example.ecommerce_client.models.Order
import com.example.ecommerce_client.models.Product
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager {

    private val db = FirebaseFirestore.getInstance()

    // CRUD operations for Product collection

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

    fun addCategory(category: Category, onSuccess: () -> Unit, onFailure: () -> Unit) {
        db.collection("Category")
            .add(category)
            .addOnSuccessListener { onSuccess() }
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
