package org.ybk.fooddiaryapp.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.ybk.fooddiaryapp.data.model.diary.FoodImage

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageItems")
    fun setOriginalDiaryImages(
        recyclerView: RecyclerView,
        imageItems: List<FoodImage>) {
        if(recyclerView.adapter == null) {
            recyclerView.adapter = FoodImageAdapter(imageItems as ArrayList<FoodImage>)
        }
        val foodImageAdapter = recyclerView.adapter as FoodImageAdapter
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadFoodImage(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .thumbnail(0.1f)
                .into(imageView)
                //.onLoadFailed(imageView.context.getDrawable(R.drawable.gray_background))
    }
}
