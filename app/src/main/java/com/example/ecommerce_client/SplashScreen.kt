package com.example.ecommerce_client

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerce_client.clientPackage.SharedPreferencesManager
import com.example.ecommerce_client.clientPackage.activities.ClientMainActivity
import com.example.ecommerce_client.clientPackage.activities.SignUpActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splash_screen)
        sharedPreferencesManager = SharedPreferencesManager(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val customerId = sharedPreferencesManager.getString("adminId", "")
            if (customerId.isEmpty()){
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, ClientMainActivity::class.java))
                finish()
            }
        }, 1000)
        Handler(Looper.getMainLooper()).postDelayed({
            val customerId = sharedPreferencesManager.getString("customerId", "")
            if (customerId.isEmpty()){
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, ClientMainActivity::class.java))
                finish()
            }
        }, 1000)
    }
}