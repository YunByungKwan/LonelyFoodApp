package org.ybk.fooddiaryapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.Diary
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    /**
     * 앱에 첫 진입인지 판별한다
     */
    fun isVisited(): Boolean {
        val name = MyApplication.context().getString(R.string.first)
        val pref = MyApplication.context().getSharedPreferences(name, MODE_PRIVATE)
        val isVisited = pref.getString(MyApplication.context().getString(R.string.is_visited), "")
        return isVisited == "YES"
    }

    /**
     * 앱에 이미 방문했음을 저장한다
     */
    @SuppressLint("CommitPrefEdits")
    fun saveFirstVisitApp() {
        Timber.d(">>>>>>>>>>>>>>>>> saveFirst()")
        val pref = MyApplication.context().getSharedPreferences(Constants.FIRST, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(MyApplication.context().getString(R.string.is_visited), "YES")
        editor.apply()
    }

    /**
     * 클립보드에 복사한다
     */
    fun copyToClipboard(activity: Activity, context: Context, message: String) {
        val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("text", message)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, context.getString(R.string.clipboard), Toast.LENGTH_SHORT).show()
    }

    /** 앱 버전 구하기 */
    fun getAppVersion(context: Context): String {
        val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return info.versionName
    }

    /**
     * 회원의 고독 지수를 계산한다
     */
    fun getLevel(total: Int): Int {
        Timber.d(">>>>>>>>>>>>>>>>> getLevel(total=$total)")
        var level = 0
        for(i in 0..1000 step 10) {
            level++
            if(i <= total && total < i + 10) {
                Timber.d(">>>>>>>>>>>>>>>>> $i <= total < ${i + 10}")
                Timber.d(">>>>>>>>>>>>>>>>> level=$level")
                break
            }
        }
        return level
    }

    fun loadingDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_lottie)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    /**
     * Deep copy Diary object
     */
    fun deepCopyDiary(oldDiary: Diary): Diary {
        return Diary(oldDiary.id, oldDiary.email, oldDiary.contents, oldDiary.name, oldDiary.address,
            oldDiary.mapx, oldDiary.mapy, oldDiary.registerTime, oldDiary.updateTime,
            oldDiary.open, oldDiary.imageList)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(timeMillis: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd 방문")
        val date = Date(timeMillis.toLong())
        return sdf.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeFormat(timeMillis: String): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm a")
        val writtenDate = Date(timeMillis.toLong())
        return sdf.format(writtenDate)
    }

    /** 현재 시간을 구한다. */
    fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

    fun getImageFileName(email: String, registerTime: String): String {
        return "${convertDotToDash(email)}-${registerTime}.jpeg"
    }
    fun convertDotToDash(src: String): String {
        return src.replace('.', '_')
    }

    /**
     * 짧은 토스트
     * */
    fun showShortToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun convertDiaryToHashMap(diary: Diary): HashMap<String, Any> {
        return HashMap<String, Any>().apply {
            this[Constants.DIARY_ID] = diary.id
            this[Constants.DIARY_EMAIL] = diary.email
            this[Constants.DIARY_CONTENTS] = diary.contents
            this[Constants.DIARY_NAME] = diary.name
            this[Constants.DIARY_ADDRESS] = diary.address
            this[Constants.DIARY_MAPX] = diary.mapx
            this[Constants.DIARY_MAPY] = diary.mapy
            this[Constants.DIARY_REGISTER_TIME] = diary.registerTime
            this[Constants.DIARY_UPDATE_TIME] = diary.registerTime
            this[Constants.DIARY_IS_OPEN] = diary.open
            this[Constants.DIARY_IMAGE_LIST] = diary.imageList
        }
    }

    fun showNetworkErrorDialog(context: Context) {
        android.app.AlertDialog.Builder(context)
            .setTitle("네트워크 오류")
            .setMessage("네트워크가 연결되어 있지 않아요!")
            .setPositiveButton("닫기") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}