package com.example.ecommerce_client
import android.app.Application
import androidx.room.Room
import com.example.ecommerce_client.clientPackage.cart.CartDatabase

class MyApp : Application() {

    companion object {
        lateinit var database: CartDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            CartDatabase::class.java,
            "my_database"
        )  .fallbackToDestructiveMigration() // this is only for testing,
            //migration will be added for release
            .allowMainThreadQueries() // this solved the issue but remember that its For testing purpose only
            .build();
    }
}