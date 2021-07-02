package org.ybk.fooddiaryapp.util

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.ybk.fooddiaryapp.BuildConfig
import org.ybk.fooddiaryapp.di.component.AppComponent
import org.ybk.fooddiaryapp.di.component.DaggerAppComponent
import timber.log.Timber

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        DEBUG = isDebuggable(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false

        val pm = context.packageManager
        try {
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e.message.toString())
        }
        return debuggable
    }

    companion object {
        lateinit var instance: MyApplication
        var DEBUG = false

        fun context(): Context = instance.applicationContext
    }
}