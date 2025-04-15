package com.android.hci.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.hci.utility.MyApplication
import com.android.hci.R

class LoginActivity : AppCompatActivity() {
    private lateinit var myApp: MyApplication
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Essential: Call super first
        setContentView(R.layout.activity_login) // Set layout before finding views

        // --- Initialize myApp HERE ---
        // applicationContext is safely available after super.onCreate()
        myApp = applicationContext as MyApplication

        // --- Initialize Views HERE ---
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val textViewSignUpLink: TextView = findViewById(R.id.textViewSignUpLink)

        // --- Check login status in onCreate (after myApp and views are initialized) ---
        if (myApp.isUserLoggedIn()) {
            Log.d("LoginActivity", "Session active, pre-filling details.")
            val userEmail = myApp.currentUserSession?.userEmail
            val user = userEmail?.let { myApp.registeredUsers[it] } // Find user based on email

            userEmail?.let { email ->
                editTextEmail.setText(email) // Set email if found
            }
            user?.let { registeredUser ->
                // !! Be very careful storing/retrieving passwords like this in a real app !!
                editTextPassword.setText(registeredUser.password_insecure) // Set password if user found
            }
        } else {
            Log.d("LoginActivity", "No active session.")
        }
        // --- End of session check ---

        // --- Set Click Listeners ---
        textViewSignUpLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString() // No trim for password

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // myApp is guaranteed to be initialized here because it's done earlier in onCreate
                if (myApp.loginUser(email, password)) {
                    Toast.makeText(this, "Login Successful (Prototype)!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this, "Login Failed: Invalid email or password.", Toast.LENGTH_LONG).show()
                    // Optionally clear password field: editTextPassword.text.clear()
                    editTextPassword.requestFocus()
                }
            } else {
                // Give more specific feedback
                if (email.isEmpty()) {
                    editTextEmail.error = "Email is required"
                    editTextEmail.requestFocus()
                } else {
                    editTextPassword.error = "Password is required"
                    editTextPassword.requestFocus()
                }
                Toast.makeText(this, "Login Failed: Email and password cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    } // End of onCreate
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}