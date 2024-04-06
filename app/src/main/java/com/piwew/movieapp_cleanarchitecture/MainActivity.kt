package com.piwew.movieapp_cleanarchitecture

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.piwew.movieapp_cleanarchitecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        with(binding) {
            navView.setupWithNavController(navController)
            navView.setOnItemSelectedListener { menuItem ->
                if (!menuItem.isChecked) {
                    val uri = when (menuItem.itemId) {
                        R.id.navigation_home -> "myapp://homeFragment"
                        R.id.navigation_settings -> "myapp://settingsFragment"
                        else -> null
                    }
                    uri?.let { navController.navigate(Uri.parse(it)) }
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}