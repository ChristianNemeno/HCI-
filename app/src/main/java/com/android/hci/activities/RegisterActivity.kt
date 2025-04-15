package com.android.hci.activities



import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns // For email validation
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.android.hci.MyApplication
import com.android.hci.PrototypeUser
import com.android.hci.R
import com.google.android.material.imageview.ShapeableImageView
import java.text.SimpleDateFormat
import java.util.* // Import Calendar and Locale

class RegisterActivity : AppCompatActivity() {

    // Declare Views - using lateinit for non-nullables initialized in onCreate
    private lateinit var editTextName: EditText
    private lateinit var editTextEmailRegister: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextDOB: EditText
    private lateinit var editTextPasswordRegister: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonCreateAccount: AppCompatButton
    private lateinit var textViewLoginLink: TextView
    private lateinit var imageViewProfilePic: ShapeableImageView

    // Calendar instance for Date Picker
    private val dobCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Views using findViewById
        // Consider using View Binding for better null safety and conciseness
        editTextName = findViewById(R.id.editTextName)
        editTextEmailRegister = findViewById(R.id.editTextEmailRegister)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextDOB = findViewById(R.id.editTextDOB)
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount)
        textViewLoginLink = findViewById(R.id.textViewLoginLink)
        imageViewProfilePic = findViewById(R.id.imageViewProfilePic)

        // Set OnClickListener for the LOGIN link using a lambda
        textViewLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Set OnClickListener for the Create Account button
        buttonCreateAccount.setOnClickListener {
            if (validateInput()) { // Use your validation logic
                val name = editTextName.text.toString().trim()
                val email = editTextEmailRegister.text.toString().trim()
                val phone = editTextPhone.text.toString().trim() // Get phone
                val dob = editTextDOB.text.toString().trim()     // Get DOB
                val password = editTextPasswordRegister.text.toString() // Get password

                // Create user object for prototype
                val newUser = PrototypeUser(
                    name = name,
                    email = email,
                    phone = phone,
                    dob = dob,
                    password_insecure = password // Storing password - PROTOTYPE ONLY
                )

                // Get instance of MyApplication
                val myApp = applicationContext as MyApplication

                // Try to register the user in MyApplication
                if (myApp.registerUser(newUser)) {
                    Toast.makeText(this, "Registration Successful (Prototype)!", Toast.LENGTH_SHORT).show()

                    // --- Optional: Automatically log in after registration ---
                    myApp.startUserSession(newUser.name, newUser.email)

                    // Navigate to MainActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                } else {
                    // Registration failed (email likely already exists)
                    Toast.makeText(this, "Registration Failed: Email might already be registered.", Toast.LENGTH_LONG).show()
                    editTextEmailRegister.error = "Email already exists"
                    editTextEmailRegister.requestFocus()
                }
            }
        }

        // Set OnClickListener for Date of Birth EditText to show DatePickerDialog
        editTextDOB.setOnClickListener {
            showDatePickerDialog()
        }

        // Set OnClickListener for Profile Image
        imageViewProfilePic.setOnClickListener {
            // --- Placeholder for Image Selection Logic ---
            // Intent to pick image from gallery or camera
            Toast.makeText(this, "Image selection logic goes here!", Toast.LENGTH_SHORT).show()
        }
    }

    // --- Helper method to show Date Picker ---
    private fun showDatePickerDialog() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                dobCalendar.set(Calendar.YEAR, year)
                dobCalendar.set(Calendar.MONTH, monthOfYear)
                dobCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        DatePickerDialog(
            this,
            dateSetListener,
            // Use Calendar constants for initial date
            dobCalendar.get(Calendar.YEAR),
            dobCalendar.get(Calendar.MONTH),
            dobCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // --- Helper method to update DOB EditText label ---
    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy" // Choose your desired format
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        editTextDOB.setText(sdf.format(dobCalendar.time))
    }

    // --- Basic Input Validation Example in Kotlin ---
    private fun validateInput(): Boolean {
        val name = editTextName.text.toString().trim()
        val email = editTextEmailRegister.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val dob = editTextDOB.text.toString().trim() // Check if date was selected
        val password = editTextPasswordRegister.text.toString() // No trim for password
        val confirmPassword = editTextConfirmPassword.text.toString()

        if (name.isBlank()) { // Use isBlank for better check than isEmpty
            editTextName.error = "Name is required"
            editTextName.requestFocus()
            return false
        }
        if (email.isBlank()) {
            editTextEmailRegister.error = "Email is required"
            editTextEmailRegister.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailRegister.error = "Enter a valid email"
            editTextEmailRegister.requestFocus()
            return false
        }
        // Add more validation for phone if needed (e.g., length, digits only)
        // if (phone.isBlank()) { ... }

        if (dob.isBlank()) {
            // Show toast as error on non-focusable field is tricky
            Toast.makeText(this, "Please select Date of Birth", Toast.LENGTH_SHORT).show()
            // You could visually highlight the DOB field or show a general message
            return false
        }
        if (password.isEmpty()) { // isEmpty is fine for password check
            editTextPasswordRegister.error = "Password is required"
            editTextPasswordRegister.requestFocus()
            return false
        }
        if (password.length < 6) { // Example: Minimum password length
            editTextPasswordRegister.error = "Password must be at least 6 characters"
            editTextPasswordRegister.requestFocus()
            return false
        }
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.error = "Confirm your password"
            editTextConfirmPassword.requestFocus()
            return false
        }
        if (password != confirmPassword) { // Direct comparison works for strings in Kotlin
            editTextConfirmPassword.error = "Passwords do not match"
            editTextConfirmPassword.requestFocus()
            // Optionally clear confirm password field: editTextConfirmPassword.setText("")
            return false
        }

        // All checks passed
        return true
    }
}