package org.ybk.fooddiaryapp.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.databinding.RecentLocationItemBinding
import org.ybk.fooddiaryapp.util.Utils

class RecentLocationAdapter: RecyclerView.Adapter<RecentLocationAdapter.RecentLocationViewHolder>() {

    private val diaryList = ArrayList<Diary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentLocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RecentLocationItemBinding>(
            inflater, R.layout.recent_location_item, parent, false)
        return RecentLocationViewHolder(binding)
    }

    override fun getItemCount(): Int = diaryList.size

    override fun onBindViewHolder(holder: RecentLocationViewHolder, position: Int) {
        holder.bind(diaryList[position])
    }

    fun updateItems(items: ArrayList<Diary>) {
        diaryList.clear()
        diaryList.addAll(items)
    }

    inner class RecentLocationViewHolder(
        private val binding: RecentLocationItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

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