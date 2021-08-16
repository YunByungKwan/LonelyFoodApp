package org.ybk.fooddiaryapp.util

import android.Manifest

class Constants {

    companion object {
        const val TAG = "FoodDiaryApp"
        const val DIARY_LIST = "diary_list"
        const val PROFILE_LIST = "profile_list"
        const val IMAGE_FOLDER = "foodImage"
        const val PROFILE_IMAGE_FOLDER = "profileImage"
        const val PERM_CAMERA = Manifest.permission.CAMERA
        const val PERM_READ_ST = Manifest.permission.READ_EXTERNAL_STORAGE
        const val PERM_WRITE_ST = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val PERM_ACC_FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION
        const val PERM_ACC_COARSE_LOC = Manifest.permission.ACCESS_COARSE_LOCATION

        const val PERM_LOCATION_CODE = 111

        const val IS_OPEN = "Y"

        const val SORT_BY_LATEST = 1
        const val SORT_BY_OLDEST = 2

        const val ROOT = "users"
        const val RECOMMENDATION_LIST = "recommendation_list"
        const val DIARY = "diary"


        const val DIARY_PARAM_ID = "id"
        const val DIARY_PARAM_EMAIL = "email"
        const val DIARY_PARAM_CONTENTS = "contents"
        const val DIARY_PARAM_NAME = "name"
        const val DIARY_PARAM_ADDRESS = "address"
        const val DIARY_PARAM_MAPX = "mapx"
        const val DIARY_PARAM_MAPY = "mapy"
        const val DIARY_PARAM_REGISTERTIME = "register_time"
        const val DIARY_PARAM_UPDATETIME = "update_time"
        const val DIARY_PARAM_OPEN = "open"
        const val DIARY_PARAM_IMAGELIST = "image_list"

        /** Parameters about Diary */
        const val DIARY_ID = "id"
        const val DIARY_EMAIL = "email"
        const val DIARY_CONTENTS = "contents"
        const val DIARY_NAME = "name"
        const val DIARY_ADDRESS = "address"
        const val DIARY_MAPX = "mapx"
        const val DIARY_MAPY = "mapy"
        const val DIARY_REGISTER_TIME = "registerTime"
        const val DIARY_UPDATE_TIME = "updateTime"
        const val DIARY_IS_OPEN = "open"
        const val DIARY_IMAGE_LIST = "imageList"

        const val IMAGE_EMAIL = "email"
        const val IMAGE_LOCAL_PATH = "localPath"
        const val IMAGE_SERVER_PATH = "serverPath"

        const val SPLASH_TIME: Long = 2000
        const val CHECK_PERMS = 2001
        const val GOOGLE_LOGIN = 1000
        const val MAX_IMAGE_COUNT = 4

        const val FIRST = "First"

        const val PICK_FROM_CAMERA = 0
        const val PICK_FROM_GALLERY = 1
        const val CROP_FROM_CAMERA = 2
    }
}