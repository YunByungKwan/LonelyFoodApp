package org.ybk.fooddiaryapp.util.compat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.util.Constants
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
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
        Log.d("TAG", "createImageFile")
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        Log.d("TAG", "임시 경로: ${storageDir?.absolutePath}")
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    /**
     * inSampleSize로 샘플링해서 비트맵으로 변환한다
     */
    fun uriToBitmap(fileUri: Uri, inSampleSize: Int): Bitmap {
        val options = BitmapFactory.Options().apply {
            this.inSampleSize = inSampleSize
        }
        val filePath = fileUri.toString().removePrefix("file://")
        return BitmapFactory.decodeFile(filePath, options)
    }

    @SuppressLint("SimpleDateFormat")
    fun bitmapToFile(context: Context, bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int): String {
        val storage = context.cacheDir
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "RESIZED_$timeStamp.jpg"
        val imageFile = File(storage, imageFileName)
        try {
            imageFile.createNewFile()
            val out = FileOutputStream(imageFile)
            bitmap.compress(format, quality, out) // Bitmap.CompressFormat.JPEG 100
            out.close()
        } catch(e: FileNotFoundException) {
            Log.e("TAG", "${e.message}")
        } catch(e: IOException) {
            Log.e("TAG", "${e.message}")
        }
        return "${context.cacheDir}/$imageFileName"
    }

    /**
     * file path -> "file://" + file path 형식의 uri로 변환
     */
    fun filePathToUri(filePath: String): Uri {
        return Uri.parse("file://$filePath")
    }
}