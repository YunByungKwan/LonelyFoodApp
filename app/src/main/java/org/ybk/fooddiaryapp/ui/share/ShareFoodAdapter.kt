package org.ybk.fooddiaryapp.ui.share

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.databinding.EatenFoodGridItemBinding
import org.ybk.fooddiaryapp.ui.dialog.share.ShareDialog

class ShareFoodAdapter(
    private val fragment: Fragment
): RecyclerView.Adapter<ShareFoodAdapter.ShareFoodViewHolder>() {

    private val diaryList = ArrayList<Diary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<EatenFoodGridItemBinding>(
            inflater, R.layout.eaten_food_grid_item, parent, false)
        return ShareFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShareFoodViewHolder, position: Int) {
        holder.bind(diaryList[position])
    }

    override fun getItemCount(): Int = diaryList.size

    fun updateItems(items: ArrayList<Diary>) {
        diaryList.clear()
        diaryList.addAll(items)
    }

    inner class ShareFoodViewHolder(
        private val binding: EatenFoodGridItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: Diary) {
            binding.diaryItem = diary

            itemView.setOnClickListener {
                ShareDialog(diary).show(fragment.parentFragmentManager,"Dialog")
            }

            binding.executePendingBindings()
        }
    }
}