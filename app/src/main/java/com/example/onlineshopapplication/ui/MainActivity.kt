package com.example.onlineshopapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding


    private lateinit var navHost:NavHostFragment

    var hashCode = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        binding.bottomNav.apply {
            setupWithNavController(navHost.navController)
            // disable double click on items
            setOnNavigationItemReselectedListener {}
        }
        navHost.navController.addOnDestinationChangedListener{_,destination,_ ->
            binding.apply {
                when(destination.id){
                    else -> bottomNav.isVisible = false
                }
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }
}