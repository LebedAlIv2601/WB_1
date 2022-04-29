package com.example.wb_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wb_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navController = findNavController(R.id.nav_host_fragment)

        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_service,
            R.id.navigation_broadcast_receiver,
            R.id.navigation_content_provider))
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding?.bottomNavigationView?.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_service -> {
                    navController.navigate(R.id.navigation_service, null, navOptions)
                }
                R.id.navigation_broadcast_receiver -> {
                    navController.navigate(R.id.navigation_broadcast_receiver, null, navOptions)
                }
                R.id.navigation_content_provider -> {
                    navController.navigate(R.id.navigation_content_provider, null, navOptions)
                }
            }
            true
        }

        binding?.bottomNavigationView?.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding?.bottomNavigationView?.selectedItemId != R.id.navigation_service){
            binding?.bottomNavigationView?.selectedItemId = R.id.navigation_service
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }


}