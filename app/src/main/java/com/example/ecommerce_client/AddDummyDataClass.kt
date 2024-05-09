package com.example.ecommerce_client

import com.example.ecommerce_client.models.Category
import com.example.ecommerce_client.models.Product

class AddDummyDataClass {
    // First, create instances of your FirebaseManager class
    val firebaseManager = FirebaseManager()

    init {

// Add categories
        firebaseManager.addCategory(
            Category(
                id = "vegetables",
                title = "Vegetables",
                imageUrl = "https://www.hsph.harvard.edu/nutritionsource/wp-content/uploads/sites/30/2012/09/vegetables-and-fruits-farmers-market.jpg",
                description = "Fresh vegetables"
            ),
            onSuccess = { categoryId ->
                // Add products for Vegetables category
                firebaseManager.addProduct(
                    Product(
                        id =  System.currentTimeMillis().toString(),
                        name = "Tomato",
                        description = "Fresh red tomatoes",
                        categoryId = categoryId.id,
                        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Tomato_je.jpg/1200px-Tomato_je.jpg",
                        price = 1.99,
                        cost = 1.00,
                        stock = 100,
                        weight = 0.2
                    ),
                    onSuccess = {
                        // Product added successfully
                    },
                    onFailure = {
                        // Failed to add product
                    }
                )

                firebaseManager.addProduct(
                    Product(
                        id =  System.currentTimeMillis().toString(),
                        name = "Potato",
                        description = "Organic potatoes",
                        categoryId = categoryId.id,
                        imageUrl = "https://m.media-amazon.com/images/I/313dtY-LOEL._AC_UF1000,1000_QL80_DpWeblab_.jpg",
                        price = 2.49,
                        cost = 1.50,
                        stock = 80,
                        weight = 0.3
                    ),
                    onSuccess = {
                        // Product added successfully
                    },
                    onFailure = {
                        // Failed to add product
                    }
                )
            },
            onFailure = {
                // Failed to add category
            }
        )
        firebaseManager.addCategory(
            Category(
                id = "groceries",
                title = "Groceries",
                imageUrl = "https://everydaythrifty.com/wp-content/uploads/2021/06/The-Ultimate-Cheap-Grocery-List-Shopping-Cart.jpg",
                description = "Essential groceries"
            ),
            onSuccess = { categoryId ->
                // Add products for Groceries category
                firebaseManager.addProduct(
                    Product(
                        id =  System.currentTimeMillis().toString(),
                        name = "Rice",
                        description = "Long-grain rice",
                        categoryId = categoryId.id,
                        imageUrl = "https://cdn.britannica.com/17/176517-050-6F2B774A/Pile-uncooked-rice-grains-Oryza-sativa.jpg",
                        price = 5.99,
                        cost = 4.00,
                        stock = 50,
                        weight = 1.0
                    ),
                    onSuccess = {
                        // Product added successfully
                    },
                    onFailure = {
                        // Failed to add product
                    }
                )

                firebaseManager.addProduct(
                    Product(
                        id =  System.currentTimeMillis().toString(),
                        name = "Pasta",
                        description = "Italian pasta",
                        categoryId = categoryId.id,
                        imageUrl = "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2021/02/05/Baked-Feta-Pasta-4_s4x3.jpg.rend.hgtvcom.1280.1280.suffix/1615916524567.jpeg",
                        price = 3.49,
                        cost = 2.00,
                        stock = 60,
                        weight = 0.5
                    ),
                    onSuccess = {
                        // Product added successfully
                    },
                    onFailure = {
                        // Failed to add product
                    }
                )
            },
            onFailure = {
                // Failed to add category
            }
        )

// Add Fruits category
        firebaseManager.addCategory(
            Category(
                id = "fruits",
                title = "Fruits",
                imageUrl = "https://www.healthyeating.org/images/default-source/home-0.0/nutrition-topics-2.0/general-nutrition-wellness/2-2-2-3foodgroups_fruits_detailfeature.jpg",
                description = "Fresh fruits"
            ),
            onSuccess = { categoryId ->
                // Add products for Fruits category
                firebaseManager.addProduct(
                    Product(
                        id =  System.currentTimeMillis().toString(),
                        name = "Apple",
                        description = "Fresh green apples",
                        categoryId = categoryId.id,
                        imageUrl = "https://naturals.pk/cdn/shop/products/apple-kalakulu.jpg?v=1593218784",
                        price = 0.99,
                        cost = 0.50,
                        stock = 120,
                        weight = 0.25
                    ),
                    onSuccess = {
                        // Product added successfully
                    },
                    onFailure = {
                        // Failed to add product
                    }
                )

                firebaseManager.addProduct(
                    Product(
                        id =  System.currentTimeMillis().toString(),
                        name = "Banana",
                        description = "Ripe bananas",
                        categoryId = categoryId.id,
                        imageUrl = "https://media.post.rvohealth.io/wp-content/uploads/2020/08/banana-pink-background-thumb-1-732x549.jpg",
                        price = 1.49,
                        cost = 1.00,
                        stock = 100,
                        weight = 0.3
                    ),
                    onSuccess = {
                        // Product added successfully
                    },
                    onFailure = {
                        // Failed to add product
                    }
                )
            },
            onFailure = {
                // Failed to add category
            }
        )
    }

}