package org.ybk.fooddiaryapp.presentation.diary

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.util.Constants

object DiaryBindingAdapter {
    @JvmStatic
    @BindingAdapter("listData", "frag")
    fun setEatenFoodList(
        recyclerView: RecyclerView,
        items: ArrayList<Diary>?,
        fragment: Fragment?) {
        if(recyclerView.adapter == null) {
            recyclerView.adapter = DiaryListAdapter(fragment!!)
        }
        val eatenFoodAdapter = recyclerView.adapter as DiaryListAdapter
        items?.let {
            eatenFoodAdapter.submitList(items.toMutableList())
        }
    }

    @JvmStatic
    @BindingAdapter("openFlag")
    fun setVisibilityShareText(imageView: ImageView, openFlag: String) {
        if(openFlag == Constants.IS_OPEN) {
            imageView.setImageResource(R.drawable.ic_store)
        } else {
            imageView.setImageResource(R.drawable.ic_store_gray)
        }
    }

    @JvmStatic
    @BindingAdapter("storeTitle")
    fun setTextContent(textView: TextView, text: String) {
        textView.text = if(text.isEmpty()) {
            "어느 맛집"
        } else {
            if(text.length > 8) {
                "${text.substring(0, 8)}..."
            } else {
                text
            }
        }
    }
//    /**
//     * 가게 이름을 반환한다
//     */
//    private fun getName(diary: Diary): String {
//        return if(diary.name == "") {
//            "어느 맛집"
//        } else {
//            if(diary.name.length > 8) {
//                "${diary.name.substring(0, 8)}..."
//            } else {
//                diary.name
//            }
//        }
//    }
}