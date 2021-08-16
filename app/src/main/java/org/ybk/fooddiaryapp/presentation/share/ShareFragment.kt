package org.ybk.fooddiaryapp.presentation.share

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
import dagger.hilt.android.AndroidEntryPoint
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.ShareFragBinding
import org.ybk.fooddiaryapp.presentation.map.MapViewModel
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

@AndroidEntryPoint
class ShareFragment : Fragment() {

    private val shareViewModel: ShareViewModel by viewModels()

    private var dialog: Dialog? = null

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