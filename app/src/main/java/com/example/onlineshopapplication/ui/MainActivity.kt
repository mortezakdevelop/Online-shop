package com.example.onlineshopapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.ActivityMainBinding
import com.example.onlineshopapplication.ui.base.BaseActivity
import com.example.onlineshopapplication.utils.AppSignatureHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding:ActivityMainBinding


    private lateinit var navHost:NavHostFragment

    @Inject
    lateinit var signatureHelper: AppSignatureHelper

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

        //Generate Hashcode
        signatureHelper.appSignatures.forEach {
            hashCode = it
            Log.e("HashcodeLogs", "Hashcode : $hashCode")
        }
    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }
}