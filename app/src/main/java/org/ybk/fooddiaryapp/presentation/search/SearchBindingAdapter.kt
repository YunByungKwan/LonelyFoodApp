package org.ybk.fooddiaryapp.presentation.search

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.data.model.place.Place

object SearchBindingAdapter {
    @JvmStatic
    @BindingAdapter("places")
    fun setPlaceRecyclerView(
        recyclerView: RecyclerView, placeList: List<Place>?
    ) {
        if(recyclerView.adapter == null) {
            recyclerView.adapter = SearchResultAdapter()
        }
        val adapter = recyclerView.adapter as SearchResultAdapter
        placeList?.let {
            adapter.submitList(placeList.toMutableList())
        }
    }
}