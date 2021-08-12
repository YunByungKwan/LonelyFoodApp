package org.ybk.fooddiaryapp.presentation.share

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.data.model.diary.Diary

object ShareBindingAdapter {
    @JvmStatic
    @BindingAdapter("listOpenData", "frag")
    fun setOpenEatenFoodList(
        recyclerView: RecyclerView,
        items: ArrayList<Diary>?,
        fragment: Fragment?) {
        if(recyclerView.adapter == null) {
            recyclerView.adapter = ShareFoodAdapter(fragment!!)
        }
        val shareFoodAdapter = recyclerView.adapter as ShareFoodAdapter
        items?.let {
            shareFoodAdapter.updateItems(it)
            shareFoodAdapter.notifyDataSetChanged()
        }
    }
}