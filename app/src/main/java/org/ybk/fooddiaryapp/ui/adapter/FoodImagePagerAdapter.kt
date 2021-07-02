package org.ybk.fooddiaryapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.databinding.ViewPagerItemBinding

class FoodImagePagerAdapter(
    private val imagePathList: List<FoodImage>
): RecyclerView.Adapter<FoodImagePagerAdapter.FoodImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewPagerItemBinding>(
            inflater, R.layout.view_pager_item, parent, false)
        return FoodImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodImageViewHolder, position: Int) {
        holder.bind(imagePathList[position])
    }

    override fun getItemCount(): Int = imagePathList.size

    inner class FoodImageViewHolder(
        private val binding: ViewPagerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(foodImage: FoodImage) {
            binding.foodImageItem = foodImage
            binding.executePendingBindings()
        }
    }
}