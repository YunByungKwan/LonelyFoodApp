package org.ybk.fooddiaryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @property id - 이미지 id
 * @property email - 로그인 유저의 이메일
 * @property localPath - 이미지 로컬 경로
 * @property serverPath - 이미지 서버 경로
 * @property registerTime - 등록 시간
 */

@Entity(tableName = "food_image")
data class FoodImage(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "local_path")
    var localPath: String = "",

    @ColumnInfo(name = "server_path")
    var serverPath: String = "",

    @ColumnInfo(name = "register_time")
    var registerTime: String = ""
) {
    override fun toString(): String {
        return "\nFoodImage(" +
                "\nid='$id'"  +
                "\nemail='$email'" +
                "\nlocalPath='$localPath'" +
                "\nserverPath='$serverPath'" +
                "\nregisterTime='$registerTime'" +
                "\n)"
    }
}