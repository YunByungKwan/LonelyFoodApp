package org.ybk.fooddiaryapp.util

import android.content.Context
import android.view.View
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun InfoWindow.setDataAndOpen(
    context: Context,
    title: String,
    address: String,
    marker: Marker
) {
    adapter = object : InfoWindow.DefaultTextAdapter(context) {
        override fun getText(infoWindow: InfoWindow): CharSequence {
            return "맛집명: $title\n" +
                    "주소: ${address}\n" +
                    "\n" +
                    "검색한 장소가 맞다면 클릭!"
        }
    }
    open(marker)
}