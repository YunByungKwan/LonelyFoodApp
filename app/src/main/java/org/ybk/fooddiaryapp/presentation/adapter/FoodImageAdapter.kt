package org.ybk.fooddiaryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.databinding.FoodImageListItemBinding

class FoodImageAdapter: ListAdapter<FoodImage, FoodImageAdapter.ViewHolder>(foodImageDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FoodImageListItemBinding>(
            inflater, R.layout.food_image_list_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: FoodImageListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val arrowUp: com.airbnb.lottie.LottieAnimationView = itemView.findViewById(R.id.lottie_arrow_up)
        private val arrowDown: com.airbnb.lottie.LottieAnimationView = itemView.findViewById(R.id.lottie_arrow_down)

        fun bind(foodImage: FoodImage) {
            binding.foodImageItem = foodImage
            arrowUp.bringToFront()
            arrowDown.bringToFront()
            binding.executePendingBindings()
        }
    }

    companion object {
        val foodImageDiffUtil = object: DiffUtil.ItemCallback<FoodImage>() {
            override fun areItemsTheSame(oldItem: FoodImage, newItem: FoodImage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FoodImage, newItem: FoodImage): Boolean {
                return oldItem == newItem
            }
        }
    }
}