package org.ybk.fooddiaryapp.presentation.diary

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.databinding.DiaryFragBinding
import org.ybk.fooddiaryapp.presentation.adddiary.AddDiaryActivity
import org.ybk.fooddiaryapp.presentation.dialog.DotDialogViewModel
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

class DiaryFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: DiaryViewModelFactory

    lateinit var diaryViewModel: DiaryViewModel

    private val email = FirebaseAuth.getInstance().currentUser?.email
    private var dialog: Dialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().applicationContext as MyApplication)
            .appComponent.diaryComponent().create().inject(this)
    }

    override fun onResume() {
        super.onResume()
        diaryViewModel.getDiaryList(email!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        diaryViewModel = ViewModelProvider(this,
            viewModelFactory).get(DiaryViewModel::class.java)

        val binding = DiaryFragBinding.inflate(layoutInflater).apply {
            fragment = this@DiaryFragment
            viewModel = diaryViewModel
            lifecycleOwner = viewLifecycleOwner // observing을 가능하게 함
        }
        setHasOptionsMenu(true) // menu가 프레그먼트에서 보이게 하기 위함

        dialog = Utils.loadingDialog(requireContext())
        dialog!!.show()

        diaryViewModel.diaryList.observe(viewLifecycleOwner, {
            if(dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.m_sort_current -> {
                diaryViewModel.sortDiaryListBy(Constants.SORT_BY_LATEST)
            }
            R.id.m_sort_past -> {
                diaryViewModel.sortDiaryListBy(Constants.SORT_BY_OLDEST)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onClickFloatingButton() {
        val direction = DiaryFragmentDirections.actionDiaryFragToAddDiaryFrag()
        findNavController().navigate(direction)
    }

    fun refreshDiaryList() {
        diaryViewModel.getDiaryList(email!!)
    }
}