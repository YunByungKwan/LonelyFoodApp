package org.ybk.fooddiaryapp.ui.share

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.data.local.entity.Diary

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