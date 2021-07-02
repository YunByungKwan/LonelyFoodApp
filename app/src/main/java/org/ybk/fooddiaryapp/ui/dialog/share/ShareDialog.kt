package org.ybk.fooddiaryapp.ui.dialog.share

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.databinding.ShareFoodDetailItemBinding
import org.ybk.fooddiaryapp.ui.adapter.FoodImagePagerAdapter

class ShareDialog(
    val diary: Diary
    ): DialogFragment() {

    private lateinit var binding: ShareFoodDetailItemBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val foodImagePagerAdapter = FoodImagePagerAdapter(diary.imageList)
        binding = ShareFoodDetailItemBinding.inflate(layoutInflater).apply {
            diaryItem = diary
            eatenFoodPager.adapter = foodImagePagerAdapter
            dotsIndicator.setViewPager2(eatenFoodPager)
        }

        return AlertDialog.Builder(activity)
            .apply {
                setView(binding.root)
            }.create()
    }

}