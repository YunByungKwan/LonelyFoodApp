package org.ybk.fooddiaryapp.presentation.profile

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.ProfileFragBinding
import org.ybk.fooddiaryapp.presentation.login.LoginActivity
import org.ybk.fooddiaryapp.presentation.map.MapViewModel
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Utils
import org.ybk.fooddiaryapp.util.compat.ImageCompat
import org.ybk.fooddiaryapp.util.compat.PermissionCompat
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ProfileViewModelFactory

    lateinit var profileViewModel: ProfileViewModel

    private var dialog: Dialog? = null
    val email = FirebaseAuth.getInstance().currentUser?.email

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().applicationContext as MyApplication)
            .appComponent.profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(
            this, viewModelFactory).get(ProfileViewModel::class.java)
        val binding = DataBindingUtil.inflate<ProfileFragBinding>(
            inflater, R.layout.profile_frag, container, false).apply {
            fragment = this@ProfileFragment
            viewmodel = profileViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        profileViewModel.getDiaryList(email!!)
        profileViewModel.getProfileImage(email)

        dialog = Utils.loadingDialog(requireContext())
        dialog!!.show()

        profileViewModel.diaryList.observe(viewLifecycleOwner, Observer { status ->
            if(dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        })
        return binding.root
    }

    fun onClickLogoutText() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout_title))
            .setMessage(getString(R.string.logout_msg))
            .setPositiveButton(R.string.ok) { _, _ ->
                FirebaseAuth.getInstance().signOut()
                LoginManager.getInstance().logOut()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    fun onClickProfileImage() {
        if(PermissionCompat.existsPermission(Constants.PERM_READ_ST)) {
            val intent = ImageCompat.getGalleryIntent()
            actionGetContentResult.launch(Intent.createChooser(intent, getString(R.string.pick_image)))
        } else {
            if(!PermissionCompat.isDenialPermission(requireActivity(), Constants.PERM_READ_ST)) {
                requestReadStoragePermission.launch(Constants.PERM_READ_ST)
            } else {
                Utils.showShortToast(requireActivity(), getString(R.string.need_read))
            }
        }
    }

    private val requestReadStoragePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            val intent = ImageCompat.getGalleryIntent()
            actionGetContentResult.launch(Intent.createChooser(intent, getString(R.string.pick_image)))
        } else {
            ImageCompat.showSettingDialog(requireContext(), Constants.PERM_READ_ST)
        }
    }

    private val actionGetContentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = result.data?.data
            CropImage
                .activity(uri)
                .setAspectRatio(1, 1)
                .start(requireContext(), this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if(resultCode == Activity.RESULT_OK) {
                val uri = result.uri
                profileViewModel.uploadProfileImage(email!!, uri.toString())
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}