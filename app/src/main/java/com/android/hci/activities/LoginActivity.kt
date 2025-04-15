package com.android.hci.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.hci.MyApplication
import com.android.hci.R

class LoginActivity : AppCompatActivity() {
    lateinit var myApp: MyApplication
    lateinit var editTextEmail : EditText;
    lateinit var editTextPassword : EditText;

    override fun onStart() {
        super.onStart()
        myApp = applicationContext as MyApplication

        if (myApp.isUserLoggedIn()) {
            Log.d("LoginActivity", "Session active, navigating to Main.")

            // Get the current user's email from the session
            val userEmail = myApp.currentUserSession?.userEmail

            // If email exists, set it in the editTextEmail field
            userEmail?.let { email ->
                editTextEmail.setText(email)

                // Find the user in registeredUsers to get the password
                val user = myApp.registeredUsers[email]
                user?.let {
                    editTextPassword.setText(it.password_insecure)
                }
            }
        } else {
            Log.d("LoginActivity", "No active session.")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views (Consider using ViewBinding)
        editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val textViewSignUpLink: TextView = findViewById(R.id.textViewSignUpLink)

        textViewSignUpLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Main on Login button click (add credential check first)
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
                Toast.makeText(this, "Login Failed: Invalid email or password.", Toast.LENGTH_LONG).show()
                editTextPassword.requestFocus()
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