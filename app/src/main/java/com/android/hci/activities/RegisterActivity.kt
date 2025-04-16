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
import com.android.hci.utility.MyApplication
import com.android.hci.utility.PrototypeUser
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

        editTextName = findViewById(R.id.editTextName)
        editTextEmailRegister = findViewById(R.id.editTextEmailRegister)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextDOB = findViewById(R.id.editTextDOB)
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount)
        textViewLoginLink = findViewById(R.id.textViewLoginLink)
        imageViewProfilePic = findViewById(R.id.imageViewProfilePic)

        textViewLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        buttonCreateAccount.setOnClickListener {
            if (validateInput()) {
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
                    password_insecure = password
                )

                val myApp = applicationContext as MyApplication


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
                    Toast.makeText(this, "Registration Failed: Email might already be registered.", Toast.LENGTH_LONG).show()
                    editTextEmailRegister.error = "Email already exists"
                    editTextEmailRegister.requestFocus()
                }
            }
        }

        editTextDOB.setOnClickListener {
            showDatePickerDialog()
        }

        imageViewProfilePic.setOnClickListener {
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
            dobCalendar.get(Calendar.YEAR),
            dobCalendar.get(Calendar.MONTH),
            dobCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        editTextDOB.setText(sdf.format(dobCalendar.time))
    }


    private fun validateInput(): Boolean {
        val name = editTextName.text.toString().trim()
        val email = editTextEmailRegister.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val dob = editTextDOB.text.toString().trim()
        val password = editTextPasswordRegister.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()

        if (name.isBlank()) {
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

        if (dob.isBlank()) {

            Toast.makeText(this, "Please select Date of Birth", Toast.LENGTH_SHORT).show()

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