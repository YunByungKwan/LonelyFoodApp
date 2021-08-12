package org.ybk.fooddiaryapp.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.databinding.RecentLocationItemBinding
import org.ybk.fooddiaryapp.presentation.diary.DiaryListAdapter.Companion.diffUtilCallback
import org.ybk.fooddiaryapp.util.Utils

class RecentLocationAdapter: ListAdapter<Diary, RecentLocationAdapter.ViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RecentLocationItemBinding>(
            inflater, R.layout.recent_location_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(
        private val binding: RecentLocationItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val name: TextView = itemView.findViewById(R.id.name_text)
        private val date: TextView = itemView.findViewById(R.id.date_text)

        fun bind(diary: Diary) {
            binding.diaryItem = diary
            name.text = if(diary.name == "") "어느 맛집" else diary.name
            date.text = Utils.getDateFormat(diary.registerTime)

            binding.executePendingBindings()
        }
    }
}