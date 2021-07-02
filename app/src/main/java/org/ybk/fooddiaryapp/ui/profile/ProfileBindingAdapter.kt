package org.ybk.fooddiaryapp.ui.profile

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber

object ProfileBindingAdapter {
    @JvmStatic
    @BindingAdapter("listRecentData")
    fun setRecentEatenFoodList(recyclerView: RecyclerView, items: ArrayList<Diary>?) {
        if(recyclerView.adapter == null) {
            recyclerView.adapter = RecentLocationAdapter()
        }
        val recentLocationAdapter = recyclerView.adapter as RecentLocationAdapter

        items?.sortByDescending { it.registerTime }

        items?.let {
            recentLocationAdapter.updateItems(it)
            recentLocationAdapter.notifyDataSetChanged()
        }
    }

    @JvmStatic
    @BindingAdapter("loadProfileImage")
    fun loadProfileImage(imageView: ImageView, imageUrl: String?) {
        Timber.d("loadProfileImage - imageUrl: $imageUrl")
        imageUrl?.let {
            Glide.with(imageView.context)
                .load(it)
                .thumbnail(0.1f)
                .into(imageView)
        }
    }


    @JvmStatic
    @BindingAdapter("getLevel")
    fun getLevel(textView: TextView, items: ArrayList<Diary>?) {
        textView.text = Utils.getLevel(items?.size ?: 0).toString()
    }

    @JvmStatic
    @BindingAdapter("getOpenListSize")
    fun getOpenEatenFoodListSize(textView: TextView, items: ArrayList<Diary>?) {
        var count = 0
        items?.forEach {
            if(it.open == Constants.IS_OPEN) {
                count++
            }
        }
        textView.text = "$count"
    }
}