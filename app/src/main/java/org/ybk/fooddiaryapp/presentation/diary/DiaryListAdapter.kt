package org.ybk.fooddiaryapp.presentation.diary

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.databinding.EatenFoodListItemBinding
import org.ybk.fooddiaryapp.presentation.adapter.FoodImagePagerAdapter
import org.ybk.fooddiaryapp.presentation.dialog.DotDialog
import org.ybk.fooddiaryapp.util.Utils

class DiaryListAdapter(
    private val fragment: Fragment
): ListAdapter<Diary, DiaryListAdapter.ViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<EatenFoodListItemBinding>(
            inflater, R.layout.eaten_food_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: EatenFoodListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val dotIcon: ImageView = itemView.findViewById(R.id.dot_image_view)
        private val imageViewPager: ViewPager2 = itemView.findViewById(R.id.eaten_food_pager)
        private val indicator: com.tbuonomo.viewpagerdotsindicator.DotsIndicator
                = itemView.findViewById(R.id.dots_indicator)
        private val updateTime: TextView = itemView.findViewById(R.id.update_time_text)

        fun bind(diary: Diary) {
            Log.d("TAG2", "DiaryListAdapter - bind()")
            binding.diary = diary

            dotIcon.setOnClickListener {
                DotDialog(diary).show(fragment.childFragmentManager,"Dialog")
            }
            updateTime.text = Utils.getTimeFormat(diary.updateTime)

            val foodImagePagerAdapter = FoodImagePagerAdapter(diary.imageList)
            imageViewPager.adapter = foodImagePagerAdapter
            indicator.setViewPager2(imageViewPager)

            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtilCallback = object: DiffUtil.ItemCallback<Diary>() {
            override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem == newItem
            }
        }
    }
}