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
        supportActionBar?.title = "Choose Your Premium Plan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        val monthlyPlanButton = findViewById<MaterialButton>(R.id.button_plan1)
        val annualPlanButton = findViewById<MaterialButton>(R.id.button_plan2)



        monthlyPlanButton.setOnClickListener {
            startPaymentProcess("Monthly Premium")
        }

        annualPlanButton.setOnClickListener {
            startPaymentProcess("Annual Premium")
        }


        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun startPaymentProcess(planName: String) {

        val paymentSuccessful = true

        if (paymentSuccessful) {
            showPaymentSuccessDialog(planName)
        } else {
            // nothing just proto
        }
    }


    private fun showPaymentSuccessDialog(planName: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_payment_success, null) //


        val titleTextView = dialogView.findViewById<TextView>(R.id.text_success_title) //
        val messageTextView = dialogView.findViewById<TextView>(R.id.text_success_message) //
        val iconImageView = dialogView.findViewById<ImageView>(R.id.image_success_icon) //
        val doneButton = dialogView.findViewById<Button>(R.id.button_done) //


        messageTextView.text = "You have successfully upgraded to $planName!"


        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setCancelable(false)

        val customDialog = builder.create()


        doneButton.setOnClickListener {
            customDialog.dismiss()
            // uncomment if you want to close the payment
            // finish()
        }

        customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        customDialog.show()
    }
}
