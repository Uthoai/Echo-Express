package com.top.best.ecommerce.echoexpress.view.dashboard.customer

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.databinding.ActivityCustomerDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerDashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCustomerDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_customer_dashboard)
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayout)

        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navDrawer,navController)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestinationId) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_customer_dashboard)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}