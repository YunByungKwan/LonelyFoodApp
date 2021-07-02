package org.ybk.fooddiaryapp.ui.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.databinding.EatenFoodListItemBinding
import org.ybk.fooddiaryapp.ui.adapter.FoodImagePagerAdapter
import org.ybk.fooddiaryapp.ui.dialog.dot.DotDialog
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Utils

class DiaryListAdapter(
    private val fragment: Fragment
): RecyclerView.Adapter<DiaryListAdapter.DiaryListViewHolder>() {

    private val diaryList = ArrayList<Diary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<EatenFoodListItemBinding>(
            inflater, R.layout.eaten_food_list_item, parent, false)
        return DiaryListViewHolder(binding)
    }

    override fun getItemCount(): Int = diaryList.size

    override fun onBindViewHolder(holder: DiaryListViewHolder, position: Int) {
        holder.bind(diaryList[position])
    }

    fun updateItems(items: ArrayList<Diary>) {
        diaryList.clear()
        diaryList.addAll(items)
    }

    inner class DiaryListViewHolder(
        private val binding: EatenFoodListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val name: TextView = itemView.findViewById(R.id.store_name_text)
        private val storeIcon: ImageView = itemView.findViewById(R.id.store_image_view)
        private val dotIcon: ImageView = itemView.findViewById(R.id.dot_image_view)
        private val imageViewPager: ViewPager2 = itemView.findViewById(R.id.eaten_food_pager)
        private val indicator: com.tbuonomo.viewpagerdotsindicator.DotsIndicator
                = itemView.findViewById(R.id.dots_indicator)
        private val updateTime: TextView = itemView.findViewById(R.id.update_time_text)

        fun bind(diary: Diary) {
            binding.diary = diary

            dotIcon.setOnClickListener {
                DotDialog(diary).show(fragment.childFragmentManager,"Dialog")
            }
            setVisibilityShareText(diary)

            name.text = getName(diary)
            name.bringToFront()
            updateTime.text = Utils.getTimeFormat(diary.updateTime)

            val foodImagePagerAdapter = FoodImagePagerAdapter(diary.imageList)
            imageViewPager.adapter = foodImagePagerAdapter
            indicator.setViewPager2(imageViewPager)

            binding.executePendingBindings()
        }

        private fun setVisibilityShareText(diary: Diary) {
            if(diary.open == Constants.IS_OPEN) {
                storeIcon.setImageResource(R.drawable.ic_store)
            } else {
                storeIcon.setImageResource(R.drawable.ic_store_gray)
            }
        }

        /**
         * 가게 이름을 반환한다
         */
        private fun getName(diary: Diary): String {
            return if(diary.name == "") {
                "어느 맛집"
            } else {
                if(diary.name.length > 8) {
                    "${diary.name.substring(0, 8)}..."
                } else {
                    diary.name
                }
            }
        }
    }
}