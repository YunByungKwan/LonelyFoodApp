package org.ybk.fooddiaryapp.util.compat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.util.Constants
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object ImageCompat {

    fun getGalleryIntent(): Intent {
        return Intent().also {
            it.action = Intent.ACTION_PICK
            it.type = "image/*"
        }
    }

    private fun getSettingIntent(context: Context): Intent {
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + context.packageName)).also {
            it.addCategory(Intent.CATEGORY_DEFAULT)
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    fun showSettingDialog(context: Context, type: String) {
        AlertDialog.Builder(context)
            .setMessage(
                getMessage(
                    context,
                    type
                )
            )
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.setting)) { _, _ ->
                val intent =
                    getSettingIntent(
                        context
                    )
                context.startActivity(intent)
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun getMessage(context: Context, type: String): String {
        return when (type) {
            Constants.PERM_CAMERA -> {
                context.getString(R.string.camera_setting)
            }
            Constants.PERM_READ_ST -> {
                context.getString(R.string.storage_setting)
            }
            Constants.PERM_ACC_FINE_LOC -> {
                context.getString(R.string.location_setting)
            }
            else -> {
                ""
            }
        }
    }

    /**
     * 이미지 파일을 생성한다
     */
    @SuppressLint("SimpleDateFormat")
    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}