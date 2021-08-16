package org.ybk.fooddiaryapp.presentation.adddiary

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import org.ybk.fooddiaryapp.BuildConfig
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.AddDiaryFragBinding
import org.ybk.fooddiaryapp.presentation.adapter.FoodImageAdapter
import org.ybk.fooddiaryapp.presentation.search.SearchActivity
import org.ybk.fooddiaryapp.util.Const
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import org.ybk.fooddiaryapp.util.compat.ImageCompat
import org.ybk.fooddiaryapp.util.compat.NetworkCompat
import org.ybk.fooddiaryapp.util.compat.PermissionCompat
import timber.log.Timber
import java.io.IOException

@AndroidEntryPoint
class AddDiaryFragment: Fragment() {

    private lateinit var binding: AddDiaryFragBinding

    private val addDiaryViewModel: AddDiaryViewModel by viewModels()

    private val currentUserEmail: String? = FirebaseAuth.getInstance().currentUser?.email
    private lateinit var dialog: Dialog
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddDiaryFragBinding.inflate(layoutInflater)
            .apply {
                this.fragment = this@AddDiaryFragment
                this.viewmodel = addDiaryViewModel
                this.lifecycleOwner = viewLifecycleOwner
            }
        dialog = Utils.loadingDialog(requireContext())
        observingData()
        return binding.root
    }

    private fun observingData() {
        addDiaryViewModel.status.observe(viewLifecycleOwner, { status ->
            if(dialog.isShowing) {
                dialog.dismiss()
            }
            if(status == Status.SUCCESS) {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.write_end))
                    .setMessage(getString(R.string.write_end_text))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        findNavController().navigate(R.id.action_addDiaryFrag_to_diaryFrag)
                    }.show()
            } else if(status == Status.FAILED) {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.add_fail))
                    .setMessage(getString(R.string.add_fail_for_network))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        findNavController().navigate(R.id.action_addDiaryFrag_to_diaryFrag)
                    }.show()
            }
        })

        addDiaryViewModel.viewImageList.observe(viewLifecycleOwner, { foodImageList ->
            setDeleteImageInfoText(foodImageList.size)

            val foodImageAdapter = FoodImageAdapter()
            binding.imageRecyclerView.adapter = foodImageAdapter
            binding.imageRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)

            binding.imageRecyclerView.setOnTouchListener { v, _ ->
                v!!.parent.requestDisallowInterceptTouchEvent(true)
                false
            }

            val simpleCallback = object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.UP or ItemTouchHelper.DOWN) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                    target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    foodImageList.removeAt(viewHolder.layoutPosition)
                    foodImageAdapter.notifyItemRemoved(viewHolder.layoutPosition)
                    addDiaryViewModel.viewImageList.value = foodImageList
                    addDiaryViewModel.imageCount.value = foodImageList.size
                }
            }
            val itemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(binding.imageRecyclerView)
        })
    }

    fun onClickLocationButton() {
        val mapIntent = Intent(activity, SearchActivity::class.java)
        mapResult.launch(mapIntent)
    }

    fun onClickCameraButton() {
        // 최대 갯수만큼 사진이 추가된 상태에서 버튼 클릭시 토스트 노출
        if(isAddedMaxImageCount()) {
            Utils.showShortToast(requireContext(), getString(R.string.max_image_info))
            return
        }

        if(PermissionCompat.existsPermission(Constants.PERM_CAMERA)) {
            dispatchTakePictureIntent()
        } else {
            if(!PermissionCompat.isDenialPermission(requireActivity(), Constants.PERM_CAMERA)) {
                requestCameraPermission.launch(Constants.PERM_CAMERA)
            } else {
                Utils.showShortToast(requireContext(), getString(R.string.need_camera))
            }
        }
    }

    fun onClickGalleryButton() {
        if(isAddedMaxImageCount()){
            Utils.showShortToast(requireContext(), getString(R.string.max_image_info))
            return
        }
        if(PermissionCompat.existsPermission(Constants.PERM_READ_ST)) {
            val intent = ImageCompat.getGalleryIntent()
            actionGetContentResult.launch(
                Intent.createChooser(intent, getString(R.string.pick_image)))
        } else {
            if(!PermissionCompat.isDenialPermission(
                    requireActivity(), Constants.PERM_READ_ST)
            ) {
                requestReadStoragePermission.launch(
                    Constants.PERM_READ_ST)
            } else {
                Utils.showShortToast(
                    requireContext(), getString(R.string.need_read))
            }
        }
    }

    fun onClickWriteCompleteButton() {
        val contents = binding.contentEditText.text.toString()
        // 일기가 비어있는 경우
        if(contents == "") {
            Utils.showShortToast(requireContext(), getString(R.string.warn_contents))
            return
        }
        // 이미지가 없는 경우
        if(addDiaryViewModel.imageCount.value == 0) {
            Utils.showShortToast(requireContext(), getString(R.string.warn_image))
            return
        }

        if(!NetworkCompat.isConnected()) {
            Utils.showNetworkErrorDialog(requireContext())
            return
        }
        dialog.show()

        currentUserEmail?.let { email ->
            addDiaryViewModel.addDiary(email)
        }
    }

    private fun setDeleteImageInfoText(imageCount: Int) {
        if(imageCount > 0) {
            binding.deleteInfoText.visibility = View.VISIBLE
        } else {
            binding.deleteInfoText.visibility = View.GONE
        }
    }

    private fun isAddedMaxImageCount(): Boolean {
        return addDiaryViewModel.imageCount.value!! >= Constants.MAX_IMAGE_COUNT
    }

    private fun dispatchTakePictureIntent() {
        try {
            val file = ImageCompat.createImageFile(requireContext())
            val uri = FileProvider.getUriForFile(requireContext(),
                BuildConfig.APPLICATION_ID + ".provider", file)
            imageUri = uri
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.resolveActivity(requireActivity().packageManager)?.also {
                takePictureResult.launch(intent)
            }
        } catch (e: IOException) {
            Timber.e(">>>>>>>>>>>>>>>>> Exception: ${e.message}")
        }
    }

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            dispatchTakePictureIntent()
        } else {
            ImageCompat.showSettingDialog(requireContext(), Constants.PERM_CAMERA)
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

    private val takePictureResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == AppCompatActivity.RESULT_OK) {
            CropImage
                .activity(imageUri)
                .setAspectRatio(1, 1)
                .start(requireActivity())
        } else {
            Timber.e(">>>>>>>>>>>>>>>>> RESULT_CANCELED")
        }
    }

    private val actionGetContentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == AppCompatActivity.RESULT_OK) {
            val contentUri = result.data?.data
            CropImage
                .activity(contentUri)
                .setAspectRatio(1, 1)
                .start(requireContext(), this)
        } else {
            Timber.e(">>>>>>>>>>>>>>>>> RESULT_CANCELED")
        }
    }

    private val mapResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val getIntent = result.data!!
            val name = getIntent.getStringExtra(Const.M_TITLE).toString()
            val latitude = getIntent.getDoubleExtra(Const.M_LAT, 0.0)
            val longitude = getIntent.getDoubleExtra(Const.M_LNG, 0.0)
            addDiaryViewModel.name.value = name
            addDiaryViewModel.mapx.value = latitude
            addDiaryViewModel.mapy.value = longitude

            val address = getIntent.getStringExtra(Const.M_ADDRESS)

            binding.locationText.text = address
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == AppCompatActivity.RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            if(resultCode == Activity.RESULT_OK) {
                val fileUri = result.uri
                val bitmap = ImageCompat.uriToBitmap(fileUri, 2)
                val filePath = ImageCompat.bitmapToFile(
                    requireContext(), bitmap, Bitmap.CompressFormat.JPEG, 100)
                val uri = ImageCompat.filePathToUri(filePath)

                currentUserEmail?.let { email ->
                    addDiaryViewModel.updateDiaryImagesInUI(email, uri)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}