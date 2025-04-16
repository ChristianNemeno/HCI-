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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myApp = applicationContext as MyApplication

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val textViewSignUpLink: TextView = findViewById(R.id.textViewSignUpLink)

        if (myApp.isUserLoggedIn()) {
            Log.d("LoginActivity", "Session active, pre-filling details.")
            val userEmail = myApp.currentUserSession?.userEmail
            val user = userEmail?.let { myApp.registeredUsers[it] }

            userEmail?.let { email ->
                editTextEmail.setText(email)
            }
            user?.let { registeredUser ->
                editTextPassword.setText(registeredUser.password_insecure)
            }
        } else {
            Log.d("LoginActivity", "No active session.")
        }

        textViewSignUpLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (myApp.loginUser(email, password)) {
                    Toast.makeText(this, "Login Successful (Prototype)!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this, "Login Failed: Invalid email or password.", Toast.LENGTH_LONG).show()
                    editTextPassword.requestFocus()
                }
            } else {
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
    }
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}