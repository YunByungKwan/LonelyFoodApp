package org.ybk.fooddiaryapp.presentation.editdiary

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.edit_diary_act.*
import org.ybk.fooddiaryapp.BuildConfig
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.databinding.EditDiaryActBinding
import org.ybk.fooddiaryapp.presentation.adapter.FoodImageAdapter
import org.ybk.fooddiaryapp.presentation.search.SearchActivity
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Utils
import org.ybk.fooddiaryapp.util.compat.ImageCompat
import org.ybk.fooddiaryapp.util.compat.PermissionCompat
import java.io.IOException
import java.util.*
import javax.inject.Inject

class EditDiaryActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModeLFactory: EditDiaryViewModelFactory

    lateinit var editDiaryViewModel: EditDiaryViewModel

    private lateinit var binding: EditDiaryActBinding

    private val email = FirebaseAuth.getInstance().currentUser?.email
    private var dialog: Dialog? = null
    private var newDiary: Diary? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication)
            .appComponent.editDiaryComponent().create().inject(this)

        super.onCreate(savedInstanceState)

        editDiaryViewModel = ViewModelProvider(this, viewModeLFactory).get(EditDiaryViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.edit_diary_act)
        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewmodel = editDiaryViewModel

        intent.let {
            val registerTime = it.getStringExtra(Constants.DIARY_REGISTER_TIME)
            val id = "${Utils.convertDotToDash(email!!)}-${registerTime}"
            editDiaryViewModel.getOriginalDiary2(id)
        }

        dialog = Utils.loadingDialog(this)

        editDiaryViewModel.diary.observe(this, Observer { diary ->
            newDiary = Utils.deepCopyDiary(diary)
            image_count_text.text = diary.imageList.size.toString()
            editDiaryViewModel.imageCount.value = diary.imageList.size
            val tempList = ArrayList<FoodImage>()
            diary.imageList.forEach {
                tempList.add(it)
            }
            editDiaryViewModel.imageList.postValue(tempList)
        })

        editDiaryViewModel.imageList.observe(this, androidx.lifecycle.Observer { imageList ->
            val foodImageAdapter = FoodImageAdapter(imageList)
            image_recycler_view.adapter = foodImageAdapter
            image_recycler_view.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)

            image_recycler_view.setOnTouchListener { v, _ ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                false
            }
            val simpleCallback = object : ItemTouchHelper
            .SimpleCallback(0, ItemTouchHelper.UP or ItemTouchHelper.DOWN) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val tempList = ArrayList<FoodImage>()
                    editDiaryViewModel.imageList.value!!.forEach { foodImage ->
                        tempList.add(foodImage)
                    }
                    tempList.removeAt(viewHolder.layoutPosition)
                    foodImageAdapter.notifyItemRemoved(viewHolder.layoutPosition)
                    editDiaryViewModel.imageList.value = tempList
                    editDiaryViewModel.imageCount.value = tempList.size
                }
            }
            val itemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(image_recycler_view)

            if(imageList.size > 0) {
                delete_info_text.visibility = View.VISIBLE
            } else {
                delete_info_text.visibility = View.GONE
            }
        })

        editDiaryViewModel.imageCount.observe(this, androidx.lifecycle.Observer { count ->
            image_count_text.text = count.toString()

            if(count > 0) {
                delete_info_text.visibility = View.VISIBLE
                delete_info_text.bringToFront()
            } else {
                delete_info_text.visibility = View.GONE
            }
        })

        editDiaryViewModel.isComplete.observe(this, androidx.lifecycle.Observer { isComplete ->
            if(isComplete) {
                if(dialog!!.isShowing) {
                    dialog!!.dismiss()
                }
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.edit_complete_title))
                    .setMessage(getString(R.string.edit_complete_msg)).setCancelable(false)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        finish()
                    }.show()
            }
        })
    }

    fun onClickLocationButton() {
        val mapIntent = Intent(applicationContext,
            SearchActivity::class.java)
        mapResult.launch(mapIntent)
    }

    fun onClickCameraButton() {
        if(isAddedMaxImageCount()) {
            Utils.showShortToast(this, getString(R.string.max_image_info))
            return
        }
        if(PermissionCompat.existsPermission(Constants.PERM_CAMERA)) {
            dispatchTakePictureIntent()
        } else {
            if(!PermissionCompat.isDenialPermission(this, Constants.PERM_CAMERA)) {
                requestCameraPermission.launch(Constants.PERM_CAMERA)
            } else {
                Utils.showShortToast(this, getString(R.string.need_camera))
            }
        }
    }

    fun onClickGalleryButton() {
        if(isAddedMaxImageCount()){
            Utils.showShortToast(this, getString(R.string.max_image_info))
            return
        }
        if(PermissionCompat.existsPermission(Constants.PERM_READ_ST)) {
            val intent = ImageCompat.getGalleryIntent()
            actionGetContentResult.launch(Intent.createChooser(intent, getString(R.string.pick_image)))
        } else {
            if(!PermissionCompat.isDenialPermission(this, Constants.PERM_READ_ST)) {
                requestReadStoragePermission.launch(Constants.PERM_READ_ST)
            } else {
                Utils.showShortToast(this, getString(R.string.need_read))
            }
        }
    }

    fun onClickEditCompleteButton() {
        val newContents = content_edit_text.text.toString()
        val updateTime = Utils.getCurrentTimeMillis().toString()

        // 일기가 비어있는 경우
        if(newContents == "") {
            Utils.showShortToast(this, getString(R.string.warn_contents))
            return
        }

        // 이미지가 없는 경우
        if(editDiaryViewModel.imageCount.value == 0) {
            Utils.showShortToast(this, getString(R.string.warn_image))
            return
        }
        // 로딩바 노출
        dialog!!.show()

        newDiary!!.contents = newContents
        newDiary!!.updateTime = updateTime
        newDiary!!.imageList = editDiaryViewModel.imageList.value!!
        editDiaryViewModel.editDiary(newDiary!!)
    }

    private fun isAddedMaxImageCount(): Boolean {
        return editDiaryViewModel.imageCount.value!! >= Constants.MAX_IMAGE_COUNT
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val file = ImageCompat.createImageFile(this)
            val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
            imageUri = uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.resolveActivity(packageManager)?.also {
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
            ImageCompat.showSettingDialog(this, Constants.PERM_CAMERA)
        }
    }

    private val requestReadStoragePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            actionGetContentResult.launch(Intent.createChooser(intent, getString(R.string.pick_image)))
        } else {
            ImageCompat.showSettingDialog(this, Constants.PERM_READ_ST)
        }
    }

    private val takePictureResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == RESULT_OK) {
            CropImage
                .activity(imageUri)
                .setAspectRatio(1, 1)
                .start(this)
        } else {
        }
    }

    private val actionGetContentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            CropImage
                .activity(uri)
                .setAspectRatio(1, 1)
                .start(this)
        } else {
        }
    }

    private val mapResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val getIntent = result.data!!
            val name = getIntent.getStringExtra(Constants.SEL_MARKER_TITLE).toString()
            val latitude = getIntent.getDoubleExtra(Constants.SEL_MARKER_LAT, 0.0)
            val longitude = getIntent.getDoubleExtra(Constants.SEL_MARKER_LNG, 0.0)
            val address = getIntent.getStringExtra(Constants.SEL_MARKER_ADDRESS)

            newDiary!!.name = name
            newDiary!!.address = address!!
            newDiary!!.mapx = latitude.toString()
            newDiary!!.mapy = longitude.toString()
            location_text.text = address
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if(resultCode == Activity.RESULT_OK) {
                val fileUri = result.uri
                val bitmap = ImageCompat.uriToBitmap(fileUri, 8)
                val filePath = ImageCompat.bitmapToFile(
                    this, bitmap, Bitmap.CompressFormat.JPEG, 100)
                val uri = ImageCompat.filePathToUri(filePath)
                editDiaryViewModel.addImageToView(email!!, uri)
            } else {
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}