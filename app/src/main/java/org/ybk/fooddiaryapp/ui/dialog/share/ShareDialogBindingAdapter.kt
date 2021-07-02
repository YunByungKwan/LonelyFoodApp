package org.ybk.fooddiaryapp.ui.dialog.share

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.ui.adapter.FoodImageAdapter

object ShareDialogBindingAdapter {

    @JvmStatic
    @BindingAdapter("list")
    fun setFoodImageViewpagerAdapter(
        viewPager: ViewPager2,
        imageList: List<FoodImage>?) {
        if(viewPager.adapter == null) {
            viewPager.adapter = FoodImageAdapter(imageList!! as ArrayList<FoodImage>)
        }
    }

    @JvmStatic
    @BindingAdapter("viewPager")
    fun setViewpagerToIndicator(
        indicator: DotsIndicator,
        viewPager: ViewPager2?
    ) {
        indicator.setViewPager2(viewPager!!)
    }
}