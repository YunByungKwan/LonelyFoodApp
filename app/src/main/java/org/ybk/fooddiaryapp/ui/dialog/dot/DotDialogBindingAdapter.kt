package org.ybk.fooddiaryapp.ui.dialog.dot

import android.widget.Button
import androidx.databinding.BindingAdapter
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication

object DotDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("shareText")
    fun setShareButtonText(
        button: Button,
        diary: Diary?) {
        if(diary?.open == Constants.IS_OPEN) {
            button.text = MyApplication.instance.getString(R.string.hide_to_others)
        } else {
            button.text = MyApplication.instance.getString(R.string.open_to_others)
        }
    }
}