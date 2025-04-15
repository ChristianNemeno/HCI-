package com.android.hci.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.hci.R
import com.google.android.material.button.MaterialButton

class PremiumPlansActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium_plans)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Choose Your Premium Plan" // Set title
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Optional: Add back button
        supportActionBar?.setDisplayShowHomeEnabled(true) // Optional: Add back button


        val monthlyPlanButton = findViewById<MaterialButton>(R.id.button_plan1) //
        val annualPlanButton = findViewById<MaterialButton>(R.id.button_plan2) //

        // --- Set Click Listeners ---

        monthlyPlanButton.setOnClickListener {
            // --- !! Add your logic here to actually process the monthly plan selection !! ---
            // For example, update user status, make a network call, etc.

            // --- Show Confirmation Dialog ---
            startPaymentProcess("Monthly Premium")
        }

        annualPlanButton.setOnClickListener {
            // --- !! Add your logic here to actually process the annual plan selection !! ---
            // For example, update user status, make a network call, etc.

            // --- Show Confirmation Dialog ---
            startPaymentProcess("Annual Premium")
        }

        // Handle toolbar back button press (optional)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun startPaymentProcess(planName: String) {
        // --- !!! ---
        //  THIS IS WHERE YOUR ACTUAL PAYMENT INTEGRATION LOGIC WOULD GO
        //  (e.g., Google Play Billing, Stripe SDK, etc.)
        // --- !!! ---

        // Simulate successful payment for now
        // In a real app, you would call showPaymentSuccessDialog ONLY
        // in the success callback/handler of your payment library.
        val paymentSuccessful = true // Simulate success

        if (paymentSuccessful) {
            showPaymentSuccessDialog(planName)
        } else {
            // Handle payment failure (e.g., show an error message)
        }
    }

    // --- Function to show the Custom Payment Success Dialog ---
    private fun showPaymentSuccessDialog(planName: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_payment_success, null) //

        // Find views within the custom dialog layout
        val titleTextView = dialogView.findViewById<TextView>(R.id.text_success_title) //
        val messageTextView = dialogView.findViewById<TextView>(R.id.text_success_message) //
        val iconImageView = dialogView.findViewById<ImageView>(R.id.image_success_icon) //
        val doneButton = dialogView.findViewById<Button>(R.id.button_done) //

        // Customize the message based on the plan
        messageTextView.text = "You have successfully upgraded to $planName!"

        // --- Create and Show the Dialog ---
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setCancelable(false) // Prevent closing by tapping outside

        val customDialog = builder.create()

        // Set action for the "Done" button
        doneButton.setOnClickListener {
            customDialog.dismiss()
            // Optional: Navigate away or close the activity after success
            // finish()
        }

        // Optional: Set transparent background for the default dialog frame
        // if you are using a custom background drawable with rounded corners
        customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        customDialog.show()
    }
}
