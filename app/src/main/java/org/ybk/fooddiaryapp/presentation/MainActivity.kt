package org.ybk.fooddiaryapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.main_act.*
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.util.BackPressCloseHandler


class MainActivity : AppCompatActivity() {

    private lateinit var backPressCloseHandler: BackPressCloseHandler
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val backButtonSubject: Subject<Long> =
        BehaviorSubject.createDefault(0L)
            .toSerialized()

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

        backButtonSubject.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe { t ->
                if(t.second - t.first < 2000L) {
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.back_key_text),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        // backPressCloseHandler.onBackPressed()
        backButtonSubject.onNext(System.currentTimeMillis())
    }
}