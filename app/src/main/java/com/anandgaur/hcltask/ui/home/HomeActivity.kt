package com.anandgaur.hcltask.ui.home

import android.content.res.ColorStateList
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anandgaur.hcltask.R
import com.anandgaur.hcltask.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_movies,
            R.id.navigation_favourits
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // set color navigation view
        setColorIconNavView()

    }

    private fun setColorIconNavView() {
        val iconsColorStates = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                ContextCompat.getColor(this, R.color.unselect_color_nav),
                ContextCompat.getColor(this, R.color.select_color_nav)
            )
        )

        val textColorStates = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                ContextCompat.getColor(this, R.color.unselect_color_nav),
                ContextCompat.getColor(this, R.color.select_color_nav)
            )
        )

        activityHomeBinding.navView.setItemIconTintList(iconsColorStates)
        activityHomeBinding.navView.setItemTextColor(textColorStates)
    }
}