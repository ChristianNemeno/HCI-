package com.android.hci.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.hci.R
import com.android.hci.fragments.ExploreFragment
import com.android.hci.fragments.HomeFragment
import com.android.hci.fragments.PlaceholderFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val premiumButton = findViewById<Button>(R.id.button_premium)
        // Set the initial fragment to HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment())
                setReorderingAllowed(true)
            }
        }

        premiumButton.setOnClickListener {
            startActivity(Intent(this, PremiumPlansActivity::class.java))
        }

        // Set up bottom navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nagivation_home -> HomeFragment()
                R.id.navigation_community -> PlaceholderFragment.newInstance("Community")
                R.id.navigation_settings -> PlaceholderFragment.newInstance("Settings")
                else -> HomeFragment() // Fallback
            }
            supportFragmentManager.commit {
                replace(R.id.fragment_container, selectedFragment)
                setReorderingAllowed(true)
            }
            true
        }
    }

}