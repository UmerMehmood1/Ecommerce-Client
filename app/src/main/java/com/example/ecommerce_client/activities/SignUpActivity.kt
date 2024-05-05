package com.example.ecommerce_client.activities
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce_client.SharedPreferencesManager
import com.example.ecommerce_client.databinding.ActivitySignUpBinding
import com.example.ecommerce_client.models.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        binding.alreadyButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.buttonSignUp.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && address.isNotEmpty()) {
                signUpUser(name, email, password, address)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpUser(name: String, email: String, password: String, address: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val customerId = user?.uid ?: ""
                    sharedPreferencesManager.saveString("customerId", customerId)
                    val customer = Customer(customerId, name, email, password, address)
                    addCustomerToDatabase(customer)
                } else {
                    Toast.makeText(this, "Sign up failed due to ${task.exception?.message}. Please try again. ", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addCustomerToDatabase(customer: Customer) {
        db.collection("Customer")
            .document(customer.id)
            .set(customer)
            .addOnSuccessListener {
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding customer to database: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
