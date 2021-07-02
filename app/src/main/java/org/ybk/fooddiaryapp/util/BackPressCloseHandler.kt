package org.ybk.fooddiaryapp.util

import android.app.Activity
import android.widget.Toast
import org.ybk.fooddiaryapp.R

class BackPressCloseHandler(val activity: Activity) {

    private var time: Long = 0

    fun onBackPressed() {
        if(System.currentTimeMillis() > time + 2000) {
            time = System.currentTimeMillis()

            Toast.makeText(
                activity,
                activity.getString(R.string.back_key_text),
                Toast.LENGTH_SHORT).show()
            return
        }

        if(System.currentTimeMillis() <= time + 2000) {
            activity.finish()
        }
    }
}