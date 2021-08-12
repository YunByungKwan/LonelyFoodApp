package org.ybk.fooddiaryapp.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.place.Place
import org.ybk.fooddiaryapp.databinding.SearchPlaceListItemBinding

class SearchResultAdapter(
    private val placeList: List<Place>
) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SearchPlaceListItemBinding>(
            inflater, R.layout.search_place_list_item, parent, false
        )
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(placeList[position])
    }

    inner class SearchResultViewHolder(
        private val binding: SearchPlaceListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val placeName: TextView = itemView.findViewById(R.id.place_name_text)

        fun bind(place: Place) {
            binding.placeItem = place
            val temp = place.title.replace("<b>", "")
            val title = temp.replace("</b>", " ")
            placeName.text = title
            itemView.setOnClickListener {
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION && mListener != null) {
                    mListener!!.onItemClick(it, pos)
                }
            }
            binding.executePendingBindings()
        }
    }
}