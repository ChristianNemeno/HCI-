package com.android.hci.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.hci.R // Make sure R is imported
import com.android.hci.activities.LoginActivity
import com.android.hci.utility.MyApplication
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val myApp = activity?.applicationContext as? MyApplication
        val userEmail = myApp?.currentUserSession?.userEmail
        val user = userEmail?.let { myApp.registeredUsers[it] }

        val logoutButton = view.findViewWithTag<TextView>("Logout") // Assuming you add android:tag="Logout"
        val editProfileButton = view.findViewWithTag<TextView>("Edit Profile") // Example

        view.findViewById<TextView>(R.id.settings_item_edit_profile).setOnClickListener {
            Toast.makeText(context, "Edit Profile Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_change_password).setOnClickListener {
            Toast.makeText(context, "Change Password Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_manage_subscription).setOnClickListener {
            // Example: Maybe navigate to PremiumPlansActivity?
            Toast.makeText(context, "Manage Subscription Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_logout).setOnClickListener {
            // Prototype logout: clear session and go to login
            myApp?.endUserSession()
            Toast.makeText(context, "Logged Out (Prototype)", Toast.LENGTH_SHORT).show()
             val intent = Intent(activity, LoginActivity::class.java)
             intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
             startActivity(intent)
             activity?.finish()
        }
        view.findViewById<SwitchMaterial>(R.id.settings_switch_push).setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(context, "Push Notifications: $isChecked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<SwitchMaterial>(R.id.settings_switch_email).setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(context, "Email Notifications: $isChecked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_theme).setOnClickListener {
            Toast.makeText(context, "Change Theme Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_text_size).setOnClickListener {
            Toast.makeText(context, "Change Text Size Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_help).setOnClickListener {
            Toast.makeText(context, "Help Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_privacy).setOnClickListener {
            Toast.makeText(context, "Privacy Policy Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.settings_item_terms).setOnClickListener {
            Toast.makeText(context, "Terms Clicked (Prototype)", Toast.LENGTH_SHORT).show()
        }

        val appVersionTextView = view.findViewById<TextView>(R.id.settings_item_app_version)
        appVersionTextView.text = "App Version: 1.0.0 (Prototype)"
        appVersionTextView.isClickable = false
        appVersionTextView.background = null


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners(view)
    }

    private fun setupClickListeners(view: View) {
        view.findViewById<TextView>(R.id.settings_item_edit_profile)?.setOnClickListener {
            handleSettingClick("Edit Profile")
        }
        view.findViewById<TextView>(R.id.settings_item_change_password)?.setOnClickListener {
            handleSettingClick("Change Password")
        }
        view.findViewById<TextView>(R.id.settings_item_manage_subscription)?.setOnClickListener {
            handleSettingClick("Manage Subscription")
        }
        view.findViewById<TextView>(R.id.settings_item_logout)?.setOnClickListener {
            handleLogout()
        }
        view.findViewById<SwitchMaterial>(R.id.settings_switch_push)?.setOnCheckedChangeListener { _, isChecked ->
            handleSwitchChange("Push Notifications", isChecked)
        }
        view.findViewById<SwitchMaterial>(R.id.settings_switch_email)?.setOnCheckedChangeListener { _, isChecked ->
            handleSwitchChange("Email Notifications", isChecked)
        }
        view.findViewById<TextView>(R.id.settings_item_theme)?.setOnClickListener {
            handleSettingClick("Theme")
        }
        view.findViewById<TextView>(R.id.settings_item_text_size)?.setOnClickListener {
            handleSettingClick("Text Size")
        }
        view.findViewById<TextView>(R.id.settings_item_help)?.setOnClickListener {
            handleSettingClick("Help & Support")
        }
        view.findViewById<TextView>(R.id.settings_item_privacy)?.setOnClickListener {
            handleSettingClick("Privacy Policy")
        }
        view.findViewById<TextView>(R.id.settings_item_terms)?.setOnClickListener {
            handleSettingClick("Terms of Service")
        }
        val appVersionTextView = view.findViewById<TextView>(R.id.settings_item_app_version)
        appVersionTextView?.text = "App Version: 1.0.0 (Prototype)"
    }

    private fun handleSettingClick(itemName: String) {
        Toast.makeText(context, "$itemName Clicked (Prototype)", Toast.LENGTH_SHORT).show()
    }

    private fun handleSwitchChange(itemName: String, isChecked: Boolean) {
        Toast.makeText(context, "$itemName: $isChecked (Prototype)", Toast.LENGTH_SHORT).show()
    }

    private fun handleLogout() {
        val myApp = activity?.applicationContext as? MyApplication
        myApp?.endUserSession()
        Toast.makeText(context, "Logged Out (Prototype)", Toast.LENGTH_SHORT).show()

         val intent = Intent(activity, LoginActivity::class.java)
         intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         startActivity(intent)
         activity?.finish()
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}