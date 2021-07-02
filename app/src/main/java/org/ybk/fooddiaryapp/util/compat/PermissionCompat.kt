package org.ybk.fooddiaryapp.util.compat

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.ybk.fooddiaryapp.util.MyApplication

object PermissionCompat {
    /**
     * 필요 권한들이 모두 있는지 확인
     *  하나라도 없으면 false
     */
    fun existAllPermission(permissions: Array<out String>): Boolean {
        for(permissionName in permissions) {
            if(!existsPermission(permissionName)) {
                return false
            }
        }
        return true
    }

    /** 권한 승인 여부 판별 */
    fun existsPermission(permissionName: String): Boolean
            = (ContextCompat.checkSelfPermission(MyApplication.context(), permissionName)
            == PackageManager.PERMISSION_GRANTED)

    /** 거절된 권한 존재 여부 판별
     * 거절된 권한이 하나라도 있을 경우 true
     */
    fun existsDenialPermission(activity: Activity, permissions: Array<out String>): Boolean {
        for(permissionName in permissions) {
            if(isDenialPermission(activity, permissionName)) {
                return true
            }
        }
        return false
    }

    /** 필요 권한이 거절되었는지 확인 */
    fun isDenialPermission(activity: Activity, permissionName: String): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)
}