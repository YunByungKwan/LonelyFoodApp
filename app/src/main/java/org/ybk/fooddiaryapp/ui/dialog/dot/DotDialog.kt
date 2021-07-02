package org.ybk.fooddiaryapp.ui.dialog.dot

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.FirebaseDatabase
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.databinding.UpdateRemoveDialogBinding
import org.ybk.fooddiaryapp.ui.dialog.detail.DetailDialog
import org.ybk.fooddiaryapp.ui.editdiary.EditDiaryActivity
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

class DotDialog(val diary: Diary): DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dotDialogViewModel: DotDialogViewModel

    private lateinit var binding: UpdateRemoveDialogBinding
    private lateinit var parentDialog: AlertDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().applicationContext as MyApplication)
            .appComponent.dotDialogComponent().create().inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = UpdateRemoveDialogBinding.inflate(layoutInflater).apply {
            diaryItem = diary
            dialog = this@DotDialog
        }

        val parentBuilder = AlertDialog.Builder(activity)
            .setView(binding.root)
            .setMessage(requireContext().getString(R.string.dot_title))
            .setNegativeButton(requireContext().getString(R.string.close)) { dialog, _ ->
                    dialog.dismiss()
                }

        parentDialog = parentBuilder.create()

        dotDialogViewModel.status.observe(this, Observer { status ->
            if (status == Status.DELETE_SUCCESS) {
                AlertDialog.Builder(activity)
                    .setTitle("삭제 완료")
                    .setMessage(requireContext().getString(R.string.del_diary_complete_msg))
                    .setPositiveButton(requireContext().getString(R.string.ok)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setNegativeButton(requireContext().getString(R.string.cancel)) { dialog, id ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        })

        return parentDialog
    }

    fun onClickDetailButton() {
        parentDialog.dismiss()
        DetailDialog(diary).show(
            this.parentFragmentManager, "")
    }

    fun onClickEditButton() {
        val intent = Intent(activity, EditDiaryActivity::class.java)
        intent.putExtra(Constants.DIARY_EMAIL, diary.email)
        intent.putExtra(Constants.DIARY_REGISTER_TIME, diary.registerTime)
        startActivity(intent)

        parentDialog.dismiss()
    }

    fun onClickRemoveButton() {
        parentDialog.dismiss()
        val title = if(diary.name.isEmpty()) "어느 맛집" else diary.name
        AlertDialog.Builder(activity)
            .setTitle("\"${title}\"")
            .setMessage(requireContext().getString(R.string.delete_diary_msg))
            .setPositiveButton(requireContext().getString(R.string.ok)) { dialog, _ ->
                dotDialogViewModel.deleteDiary(diary)
            }
            .setNegativeButton(requireContext().getString(R.string.cancel)) { dialog, id ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun onClickShareWithTextButton() {
        if(diary.name.isEmpty()) {
            Utils.showShortToast(
                requireContext(),
                requireContext().getString(R.string.need_info))
            return
        }
        parentDialog.dismiss()

        val intent = Intent(Intent.ACTION_SEND)
        val text = "[고독한 미식가]\n${diary.name}\n${diary.address}"
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        val chooser = Intent.createChooser(intent, "친구에게 공유하기")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(chooser)
    }

    fun onClickShareButton() {
        parentDialog.dismiss()

        val title = if(diary.name.isEmpty()) "어느 맛집" else diary.name
        val msg = if(diary.open == "N")
            "다른 미식가들에게 공개하시겠습니까?\n(가게명, 주소, 사진만 공개돼요!)"
        else
            "다른 미식가들에게 비공개하시겠습니까?"

        val builder = AlertDialog.Builder(activity)
            .setTitle("\"${title}\"")
            .setMessage(msg)
            .setPositiveButton(requireContext().getString(R.string.ok)) { dialog, _ ->
                openOrHideToOthers()
                dialog.dismiss()
            }
            .setNegativeButton(requireContext().getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        if(diary.address == "") {
            Utils.showShortToast(requireContext(), requireContext().getString(R.string.need_to_address))
        } else {
            builder.show()
        }
    }

    private fun openOrHideToOthers() {
        val database = FirebaseDatabase.getInstance()
        val ref = database.reference
        val key = "${Utils.convertDotToDash(diary.email)}-${diary.registerTime}"

        val open = if(diary.open == "N") Constants.IS_OPEN else "N"
        ref.child(Constants.DIARY_LIST).child(key).child("open").setValue(open)
    }
}