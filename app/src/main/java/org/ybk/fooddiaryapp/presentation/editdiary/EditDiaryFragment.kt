package org.ybk.fooddiaryapp.presentation.editdiary

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import org.ybk.fooddiaryapp.BuildConfig
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.databinding.EditDiaryFragBinding
import org.ybk.fooddiaryapp.presentation.adapter.FoodImageAdapter
import org.ybk.fooddiaryapp.presentation.search.SearchActivity
import org.ybk.fooddiaryapp.util.*
import org.ybk.fooddiaryapp.util.compat.ImageCompat
import org.ybk.fooddiaryapp.util.compat.PermissionCompat
import java.io.IOException
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EditDiaryFragment: Fragment() {

//    @Inject
//    lateinit var viewModeLFactory: EditDiaryViewModelFactory
//
//    lateinit var editDiaryViewModel: EditDiaryViewModel

    private val editDiaryViewModel: EditDiaryViewModel by viewModels()

    private lateinit var binding: EditDiaryFragBinding

    private val email = FirebaseAuth.getInstance().currentUser?.email
    private var dialog: Dialog? = null
    private var newDiary: Diary? = null
    private var imageUri: Uri? = null

    private var foodImageAdapter: FoodImageAdapter? = null

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        (requireActivity().applicationContext as MyApplication)
//            .appComponent.editDiaryComponent().create().inject(this)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        editDiaryViewModel = ViewModelProvider(this,
//            viewModeLFactory).get(EditDiaryViewModel::class.java)

        binding = EditDiaryFragBinding.inflate(layoutInflater).apply {
            this.fragment = this@EditDiaryFragment
            this.viewmodel = editDiaryViewModel
            this.lifecycleOwner = viewLifecycleOwner
        }

        val registerTime = arguments?.getString("registerTime")
        editDiaryViewModel.getDiary(email!!, registerTime!!)

        dialog = Utils.loadingDialog(requireContext())

        editDiaryViewModel.diary.observe(viewLifecycleOwner, { diary ->
            newDiary = Utils.deepCopyDiary(diary)
            editDiaryViewModel.imageCount.value = diary.imageList.size
            val tempList = ArrayList<FoodImage>()
            diary.imageList.forEach {
                tempList.add(it)
            }
            editDiaryViewModel.imageList.postValue(tempList)
        })

        editDiaryViewModel.imageList.observe(viewLifecycleOwner, { imageList ->
            foodImageAdapter = FoodImageAdapter()
            binding.imageRecyclerView.adapter = foodImageAdapter
            binding.imageRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)

            binding.imageRecyclerView.setOnTouchListener { v, _ ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                false
            }

            val itemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(binding.imageRecyclerView)
        })

        editDiaryViewModel.isComplete.observe(viewLifecycleOwner, { isComplete ->
            if(isComplete) {
                if(dialog!!.isShowing) {
                    dialog!!.dismiss()
                }
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.edit_complete_title))
                    .setMessage(getString(R.string.edit_complete_msg))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        // finish()
                    }.show()
            }
        })
        return binding.root
    }

    fun onClickLocationButton() {
        val mapIntent = Intent(activity, SearchActivity::class.java)
        mapResult.launch(mapIntent)
    }

    fun onClickCameraButton() {
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
            actionGetContentResult.launch(Intent.createChooser(intent, getString(R.string.pick_image)))
        } else {
            if(!PermissionCompat.isDenialPermission(requireActivity(), Constants.PERM_READ_ST)) {
                requestReadStoragePermission.launch(Constants.PERM_READ_ST)
            } else {
                Utils.showShortToast(requireContext(), getString(R.string.need_read))
            }
        }
    }

    fun onClickEditCompleteButton() {
        val newContents = binding.contentEditText.text.toString()
        val updateTime = Utils.getCurrentTimeMillis().toString()

        // ????????? ???????????? ??????
        if(newContents == "") {
            Utils.showShortToast(requireContext(), getString(R.string.warn_contents))
            return
        }

        // ???????????? ?????? ??????
        if(editDiaryViewModel.imageCount.value == 0) {
            Utils.showShortToast(requireContext(), getString(R.string.warn_image))
            return
        }
        // ????????? ??????
        dialog!!.show()

        newDiary!!.contents = newContents
        newDiary!!.updateTime = updateTime
        newDiary!!.imageList = editDiaryViewModel.imageList.value!!
        editDiaryViewModel.updateDiary(newDiary!!)
    }

    private fun isAddedMaxImageCount(): Boolean {
        return editDiaryViewModel.imageCount.value!! >= Constants.MAX_IMAGE_COUNT
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val file = ImageCompat.createImageFile(requireContext())
            val uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file)
            imageUri = uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.resolveActivity(requireActivity().packageManager)?.also {
                takePictureResult.launch(intent)
            }
        } catch (e: IOException) {
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
            actionGetContentResult.launch(Intent.createChooser(
                requireActivity().intent, getString(R.string.pick_image)))
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
                .start(requireActivity())
        } else {
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
            val address = getIntent.getStringExtra(Const.M_ADDRESS)

            newDiary!!.name = name
            newDiary!!.address = address!!
            newDiary!!.mapx = latitude.toString()
            newDiary!!.mapy = longitude.toString()
            binding.locationText.text = address
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if(resultCode == Activity.RESULT_OK) {
                val fileUri = result.uri
                val bitmap = ImageCompat.uriToBitmap(fileUri, 8)
                val filePath = ImageCompat.bitmapToFile(
                    requireContext(), bitmap, Bitmap.CompressFormat.JPEG, 100)
                val uri = ImageCompat.filePathToUri(filePath)
                editDiaryViewModel.addImageToView(email!!, uri)
            } else {
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP or ItemTouchHelper.DOWN) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val tempList = ArrayList<FoodImage>()
            editDiaryViewModel.imageList.value!!.forEach { foodImage ->
                tempList.add(foodImage)
            }
            tempList.removeAt(viewHolder.layoutPosition)
            foodImageAdapter?.notifyItemRemoved(viewHolder.layoutPosition)
            editDiaryViewModel.imageList.value = tempList
            editDiaryViewModel.imageCount.value = tempList.size
        }
    }
}