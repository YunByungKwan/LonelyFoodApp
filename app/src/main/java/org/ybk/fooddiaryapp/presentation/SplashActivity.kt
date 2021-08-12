package org.ybk.fooddiaryapp.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.presentation.login.LoginActivity
import org.ybk.fooddiaryapp.util.Constants

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_act)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, Constants.SPLASH_TIME)
    }
}