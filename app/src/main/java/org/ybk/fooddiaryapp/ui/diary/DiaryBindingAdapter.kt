package org.ybk.fooddiaryapp.ui.diary

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.data.local.entity.Diary

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
            eatenFoodAdapter.updateItems(it)
            eatenFoodAdapter.notifyDataSetChanged()
        }
    }
}