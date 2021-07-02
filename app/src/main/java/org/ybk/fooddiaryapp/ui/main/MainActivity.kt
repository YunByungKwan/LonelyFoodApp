package org.ybk.fooddiaryapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import kotlinx.android.synthetic.main.main_act.*
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.util.BackPressCloseHandler


class MainActivity : AppCompatActivity() {

    private lateinit var backPressCloseHandler: BackPressCloseHandler
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)

        val navController = findNavController(this, R.id.nav_host)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.eatenFoodFragment, R.id.shareFoodFragment, R.id.foodMapFragment,
            R.id.profileFragment, R.id.settingsFragment
        ))
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(main_bottom_navigation, navController)

        backPressCloseHandler = BackPressCloseHandler(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }
}