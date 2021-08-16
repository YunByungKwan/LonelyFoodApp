package org.ybk.fooddiaryapp.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.place.Place
import org.ybk.fooddiaryapp.databinding.SearchPlaceListItemBinding

class SearchResultAdapter: ListAdapter<Place, SearchResultAdapter.ViewHolder>(searchDiffUtilCallback) {

    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SearchPlaceListItemBinding = DataBindingUtil.inflate(
            inflater, R.layout.search_place_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: SearchPlaceListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { v ->
                val pos = adapterPosition
                onItemClickListener?.let { listener ->
                    if(pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, pos)
                    }
                }
            }
        }

        fun bind(place: Place) {
            with(binding) {
                placeItem = place
                executePendingBindings()
            }
        }
    }

    companion object {
        val searchDiffUtilCallback = object: DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return (oldItem.mapx == newItem.mapx) && (oldItem.mapy == newItem.mapy)
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }
}