package org.ybk.fooddiaryapp.ui.diary

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.DiaryFragBinding
import org.ybk.fooddiaryapp.ui.adddiary.AddDiaryActivity
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

class DiaryFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var diaryViewModel: DiaryViewModel

    private val email = FirebaseAuth.getInstance().currentUser?.email
    private var dialog: Dialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().applicationContext as MyApplication)
            .appComponent.diaryComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        diaryViewModel.getDiaryAll(email!!)

        val binding = DiaryFragBinding.inflate(layoutInflater).apply {
            fragment = this@DiaryFragment
            viewModel = diaryViewModel
            lifecycleOwner = viewLifecycleOwner // observing을 가능하게 함
        }

        setHasOptionsMenu(true) // menu가 프레그먼트에서 보이게 하기 위함

        dialog = Utils.loadingDialog(requireContext())
        dialog!!.show()

        diaryViewModel.diaryList.observe(viewLifecycleOwner, Observer { status ->
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
            R.id.m_sort_current ->
                diaryViewModel.sortDiaryListBy(Constants.SORT_BY_LATEST)
            R.id.m_sort_past ->
                diaryViewModel.sortDiaryListBy(Constants.SORT_BY_OLDEST)
        }
        return super.onOptionsItemSelected(item)
    }

    fun onClickFloatingButton() {
        startActivity(Intent(activity,
            AddDiaryActivity::class.java))
    }
}