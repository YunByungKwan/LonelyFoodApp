package org.ybk.fooddiaryapp.ui.share

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.ShareFragBinding
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

class ShareFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var shareViewModel: ShareViewModel

    private var dialog: Dialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().applicationContext as MyApplication)
            .appComponent.shareComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ShareFragBinding>(
            inflater, R.layout.share_frag, container, false).apply {
            fragment = this@ShareFragment
            lifecycleOwner = viewLifecycleOwner
            viewmodel = shareViewModel
        }

        shareViewModel.getOpenDiaryAll()

        dialog = Utils.loadingDialog(requireContext())
        dialog!!.show()

        shareViewModel.diaryList.observe(viewLifecycleOwner, Observer {status ->
            if(dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        })
        return binding.root
    }
}