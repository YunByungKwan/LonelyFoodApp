package org.ybk.fooddiaryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @property id - 일기 id
 * @property email - 로그인 유저의 이메일
 * @property contents - 일기 내용
 * @property name - 맛집명
 * @property address - 맛집 주소
 * @property mapx - 맛집 위치(x좌표)
 * @property mapy - 맛집 위치(y좌표)
 * @property registerTime - 등록 시간
 * @property updateTime - 수정 시간
 * @property open - 일기의 공개 여부
 * @property imageList - 일기 이미지 리스트
 */

@Entity(tableName = "diary")
data class Diary(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "contents")
    var contents: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "address")
    var address: String = "",

    @ColumnInfo(name = "mapx")
    var mapx: String = "",

    @ColumnInfo(name = "mapy")
    var mapy: String = "",

    @ColumnInfo(name = "register_time")
    var registerTime: String = "",

    @ColumnInfo(name = "update_time")
    var updateTime: String = "",

    @ColumnInfo(name = "open")
    var open: String = "",

    @ColumnInfo(name = "image_list")
    var imageList: List<FoodImage> = listOf()
) {
    override fun toString(): String {
        val sb = StringBuilder()
        imageList.forEachIndexed { index, image -> sb.append("[$index] ${image.serverPath}") }
        return "\nDiary(" +
                "\nid='$id'" +
                "\nemail='$email'" +
                "\ncontents='$contents'" +
                "\nname='$name'" +
                "\naddress='$address'" +
                "\nmapx='$mapx'" +
                "\nmapy='$mapy'" +
                "\nregisterTime='$registerTime'" +
                "\nupdateTime='$updateTime'" +
                "\nopen='$open'" +
                "\nsb)"
    }
}