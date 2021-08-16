package org.ybk.fooddiaryapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_act.*
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.MainActBinding
import org.ybk.fooddiaryapp.util.BackPressCloseHandler


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var backPressCloseHandler: BackPressCloseHandler
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: MainActBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_act)

        val navController = findNavController(this, R.id.nav_host)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.eatenFoodFragment, R.id.shareFoodFragment, R.id.foodMapFragment,
            R.id.profileFragment, R.id.settingsFragment
        ))
        NavigationUI.setupActionBarWithNavController(
            this, navController, appBarConfiguration)

        NavigationUI.setupWithNavController(
            main_bottom_navigation, navController)

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