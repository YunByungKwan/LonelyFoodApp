package org.ybk.fooddiaryapp.ui.settings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerFragment
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.SettingsFragBinding
import org.ybk.fooddiaryapp.ui.login.LoginActivity
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber
import javax.inject.Inject

class SettingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    private val email: String? = FirebaseAuth.getInstance().currentUser?.email
    private var dialog: Dialog? = null
    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().applicationContext as MyApplication)
            .appComponent.settingsComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<SettingsFragBinding>(
            inflater, R.layout.settings_frag, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@SettingsFragment
            viewmodel = settingsViewModel
        }

        dialog = Utils.loadingDialog(requireContext())
        binding.appVersionText.text = Utils.getAppVersion(requireContext())

        settingsViewModel.status.observe(viewLifecycleOwner, androidx.lifecycle.Observer { status ->
            Timber.d("observe()")
            if(status == Status.DELETE_SUCCESS) {
                Timber.d("DELETE_SUCCESS")
                settingsViewModel.withdrawFirebase()
            }else if(status == Status.WITHDRAW_SUCCESS) {
                if(this.dialog!!.isShowing) {
                    this.dialog!!.dismiss()
                }
                Timber.d("WITHDRAW_SUCCESS")
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else if(status == Status.WITHDRAW_FAILED) {
                Timber.d("WITHDRAW_FAILED")
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    fun onClickOpenSourceLicenseText() {
        navController.navigate(R.id.action_settingsFragment_to_openSourceFragment)
    }

    fun onClickLogoutText() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout_title))
            .setMessage(getString(R.string.logout_msg))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                FirebaseAuth.getInstance().signOut()
                LoginManager.getInstance().logOut()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    fun onClickWithdrawText() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.withdraw_title))
            .setMessage(getString(R.string.withdraw_msg))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                this.dialog!!.show()
                settingsViewModel.deleteDiaryAll(email!!)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}