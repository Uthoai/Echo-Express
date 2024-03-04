package com.top.best.ecommerce.echoexpress.view.dashboard.seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.databinding.ActivitySellerDashboardBinding
import com.top.best.ecommerce.echoexpress.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SellerDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellerDashboardBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //navController with appBarConfig fragment
        navController = findNavController(R.id.sellerFragmentContainerView)
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.sellerHomeFragment,
            R.id.uploadProductFragment,
            R.id.sellerProfileFragment,
        ))

        binding.sellerBottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //Seller Top Nav
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.seller_top_nav_menu, menu)
        return true
    }

    //Seller Top Nav Item Select
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuLogout ->{
                mAuth.signOut()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            R.id.menuSetting ->{
                Toast.makeText(this, "click setting", Toast.LENGTH_SHORT).show()
            }
            R.id.menuReport ->{
                Toast.makeText(this, "click report", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
