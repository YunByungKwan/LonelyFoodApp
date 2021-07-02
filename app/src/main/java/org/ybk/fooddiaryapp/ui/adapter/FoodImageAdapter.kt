package org.ybk.fooddiaryapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.databinding.FoodImageListItemBinding

class FoodImageAdapter(
    private val foodImageList: ArrayList<FoodImage>)
    : RecyclerView.Adapter<FoodImageAdapter.FoodImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FoodImageListItemBinding>(
            inflater, R.layout.food_image_list_item, parent, false)
        return FoodImageViewHolder(binding)
    }

    override fun getItemCount(): Int = foodImageList.size

    override fun onBindViewHolder(holder: FoodImageViewHolder, position: Int) {
        holder.bind(foodImageList[position])
    }

    inner class FoodImageViewHolder(
        private val binding: FoodImageListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val arrowUp: com.airbnb.lottie.LottieAnimationView = itemView.findViewById(R.id.lottie_arrow_up)
        private val arrowDown: com.airbnb.lottie.LottieAnimationView = itemView.findViewById(R.id.lottie_arrow_down)

        fun bind(foodImage: FoodImage) {
            binding.foodImageItem = foodImage
            arrowUp.bringToFront()
            arrowDown.bringToFront()
            binding.executePendingBindings()
        }
    }
}