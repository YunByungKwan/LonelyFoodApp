package org.ybk.fooddiaryapp.ui.adddiary

import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.add_diary_act.*
import org.ybk.fooddiaryapp.BuildConfig
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.AddDiaryActBinding
import org.ybk.fooddiaryapp.ui.adapter.FoodImageAdapter
import org.ybk.fooddiaryapp.ui.search.SearchActivity
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import org.ybk.fooddiaryapp.util.compat.ImageCompat
import org.ybk.fooddiaryapp.util.compat.NetworkCompat
import org.ybk.fooddiaryapp.util.compat.PermissionCompat
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddDiaryActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var addDiaryViewModel: AddDiaryViewModel

    private val email: String? = FirebaseAuth.getInstance().currentUser?.email
    private lateinit var binding: AddDiaryActBinding
    private lateinit var dialog: Dialog
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).appComponent
            .addDiaryComponent().create().inject(this)

        super.onCreate(savedInstanceState)

        binding = AddDiaryActBinding.inflate(layoutInflater).apply {
            setContentView(root)
            this.activity = this@AddDiaryActivity
            this.viewmodel = addDiaryViewModel
            this.lifecycleOwner = this@AddDiaryActivity
        }

        dialog = Utils.loadingDialog(this)

        addDiaryViewModel.status.observe(this, androidx.lifecycle.Observer { status ->
            if(dialog.isShowing) {
                dialog.dismiss()
            }
            if(status == Status.SUCCESS) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.write_end))
                    .setMessage(getString(R.string.write_end_text))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        finish()
                    }.show()
            } else if(status == Status.FAILED) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_fail))
                    .setMessage(getString(R.string.add_fail_for_network))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        finish()
                    }.show()
            }
        })

        addDiaryViewModel.viewImageList.observe(this, androidx.lifecycle.Observer { foodImageList ->
            setDeleteImageInfoText(foodImageList.size)

            val foodImageAdapter = FoodImageAdapter(foodImageList)
            image_recycler_view.adapter = foodImageAdapter
            image_recycler_view.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)

            image_recycler_view.setOnTouchListener { v, _ ->
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
            itemTouchHelper.attachToRecyclerView(image_recycler_view)
        })
    }

    fun onClickLocationButton() {
        val mapIntent = Intent(this,
            SearchActivity::class.java)
        mapResult.launch(mapIntent)
    }

    fun onClickCameraButton() {
        // 최대 갯수만큼 사진이 추가된 상태에서 버튼 클릭시 토스트 노출
        if(isAddedMaxImageCount()) {
            Utils.showShortToast(
                this, getString(R.string.max_image_info))
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
            actionGetContentResult.launch(
                Intent.createChooser(intent, getString(R.string.pick_image)))
        } else {
            if(!PermissionCompat.isDenialPermission(
                    this, Constants.PERM_READ_ST)
            ) {
                requestReadStoragePermission.launch(
                    Constants.PERM_READ_ST)
            } else {
                Utils.showShortToast(
                    this, getString(R.string.need_read))
            }
        }
    }

    fun onClickWriteCompleteButton() {
        val contents = content_edit_text.text.toString()
        // 일기가 비어있는 경우
        if(contents == "") {
            Utils.showShortToast(this, getString(R.string.warn_contents))
            return
        }
        // 이미지가 없는 경우
        if(addDiaryViewModel.imageCount.value == 0) {
            Utils.showShortToast(this, getString(R.string.warn_image))
            return
        }

        if(!NetworkCompat.isConnected()) {
            Utils.showNetworkErrorDialog(this)
            return
        }
        dialog.show()
        addDiaryViewModel.addDiary(email!!)
    }

    private fun setDeleteImageInfoText(imageCount: Int) {
        if(imageCount > 0) {
            delete_info_text.visibility = View.VISIBLE
        } else {
            delete_info_text.visibility = View.GONE
        }
    }

    private fun isAddedMaxImageCount(): Boolean {
        return addDiaryViewModel.imageCount.value!! >= Constants.MAX_IMAGE_COUNT
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val file = ImageCompat.createImageFile(this)
            val uri = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider", file)
            imageUri = uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.resolveActivity(packageManager)?.also {
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
            ImageCompat.showSettingDialog(this, Constants.PERM_CAMERA)
        }
    }

    private val requestReadStoragePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            val intent = ImageCompat.getGalleryIntent()
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
            Timber.e(">>>>>>>>>>>>>>>>> RESULT_CANCELED")
        }
    }

    private val actionGetContentResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == RESULT_OK) {
            val contentUri = result.data?.data
            CropImage
                .activity(contentUri)
                .setAspectRatio(1, 1)
                .start(this)
        } else {
            Timber.e(">>>>>>>>>>>>>>>>> RESULT_CANCELED")
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
            addDiaryViewModel.name.value = name
            addDiaryViewModel.mapx.value = latitude
            addDiaryViewModel.mapy.value = longitude

            val address = getIntent.getStringExtra(Constants.SEL_MARKER_ADDRESS)

            location_text.text = address
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            if(resultCode == Activity.RESULT_OK) {
                val fileUri = result.uri
                val bitmap = ImageCompat.uriToBitmap(fileUri, 8)
                val filePath = ImageCompat.bitmapToFile(
                    this, bitmap, Bitmap.CompressFormat.JPEG, 100)
                val uri = ImageCompat.filePathToUri(filePath)
                addDiaryViewModel.updateDiaryImagesInUI(email!!, uri)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}