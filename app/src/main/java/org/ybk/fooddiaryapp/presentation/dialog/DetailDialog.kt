package org.ybk.fooddiaryapp.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.eaten_food_detail_item.*
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.databinding.EatenFoodDetailItemBinding
import org.ybk.fooddiaryapp.presentation.adapter.FoodImagePagerAdapter
import org.ybk.fooddiaryapp.util.Utils

class DetailDialog(
    val diary: Diary
    ): DialogFragment() {

    private lateinit var binding: EatenFoodDetailItemBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val foodImagePagerAdapter = FoodImagePagerAdapter(diary.imageList)
        binding = EatenFoodDetailItemBinding.inflate(layoutInflater).apply {
            diaryItem = diary
            dialog = this@DetailDialog
            eatenFoodPager.adapter = foodImagePagerAdapter
            dotsIndicator.setViewPager2(eatenFoodPager)
        }

        return AlertDialog.Builder(activity)
            .apply {
                setView(binding.root)
            }.create()
    }

    fun onClickCopyText() {
        if(binding.locationText.text == getString(R.string.somewhere)) {
            Toast.makeText(
                context,
                getString(R.string.need_to_add_address),
                Toast.LENGTH_SHORT).show()
        } else {
            Utils.copyToClipboard(
                this.requireActivity(),
                this.requireContext(),
                binding.locationText.text.toString()
            )
        }
    }

    fun onClickCopyIcon() {
        if(binding.locationText.text == getString(R.string.somewhere)) {
            Toast.makeText(
                context,
                getString(R.string.need_to_add_address),
                Toast.LENGTH_SHORT).show()
        } else {
            Utils.copyToClipboard(
                this.requireActivity(),
                this.requireContext(),
                binding.locationText.text.toString()
            )
        }
    }
}