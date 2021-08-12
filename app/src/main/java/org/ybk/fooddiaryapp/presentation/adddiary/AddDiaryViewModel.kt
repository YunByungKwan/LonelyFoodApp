package org.ybk.fooddiaryapp.presentation.adddiary

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.UploadTask
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.data.model.etc.Response
import org.ybk.fooddiaryapp.data.model.etc.TaskResponse
import org.ybk.fooddiaryapp.data.model.etc.UploadResponse
import org.ybk.fooddiaryapp.domain.usecase.AddDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadFoodImageUseCase
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

class AddDiaryViewModel(
    private val addDiaryUseCase: AddDiaryUseCase,
    private val uploadFoodImageUseCase: UploadFoodImageUseCase
) : BaseViewModel() {

    val contents = ObservableField("")
    val address = ObservableField("")
    val name = MutableLiveData<String>()
    val mapx = MutableLiveData(0.0)
    val mapy = MutableLiveData(0.0)
    val viewImageList = MutableLiveData<ArrayList<FoodImage>>()

    val imageList = MutableLiveData<List<FoodImage>>()
    var imageCount = MutableLiveData<Int>()
    var status = MutableLiveData<Status>()

    init {
        name.value = ""
        viewImageList.value = ArrayList()
        imageCount.value = 0
        status.value = Status.READY
    }

    fun addDiary(email: String) = viewModelScope.launch {
        Log.d("TAG2", "addDiary")

        val urls = mutableListOf<String>()

        viewImageList.value?.let { imageList ->
            imageList.forEachIndexed { index, foodImage ->
                Log.d("TAG2", "index=$index")
                val response = uploadFoodImageUseCase.execute(foodImage)

                when(response) {
                    is UploadResponse.Complete -> {
                        val downloadUrl = response.uri
                        urls.add(downloadUrl)
                        if(index == viewImageList.value!!.size - 1) {
                            addDiaryToDB(email, urls)
                        }
                    }
                    is UploadResponse.Failure -> {
                        Log.e("TAG2", "Fail.")
                    }
                }
            }
        }
    }

    private fun addDiaryToDB(
        email: String, serverUrlArrayList: MutableList<String>
    ) = viewModelScope.launch {
        Log.d("TAG2", "AddDiaryViewModel - addDiaryToDB")
        for(index in serverUrlArrayList.indices) {
            viewImageList.value!![index]
                .serverPath = serverUrlArrayList[index]
        }
        val currentTime = Utils.getCurrentTimeMillis().toString()
        val id = "${Utils.convertDotToDash(email)}-${currentTime}"
        val diary = Diary(
            id, email,
            contents.get().toString(),
            name.value.toString(),
            address.get().toString(),
            mapx.value.toString(),
            mapy.value.toString(),
            currentTime,
            currentTime,
            "N",
            viewImageList.value!!)

        val response = addDiaryUseCase.execute3(diary)
        when(response) {
            is TaskResponse.Complete -> {
                status.value = Status.SUCCESS
            }
            is TaskResponse.Failure -> {
                status.value = Status.FAILED
            }
        }
    }

    fun updateDiaryImagesInUI(email: String, uri: Uri) {
        val currentTime = Utils.getCurrentTimeMillis().toString()
        val localUri = uri.toString()
        val temp = viewImageList.value
        val id = "${Utils.convertDotToDash(email)}-${currentTime}"
        temp!!.add(FoodImage(id, email, localUri, localUri, currentTime))
        viewImageList.value = temp!!
        val currentCount = imageCount.value!!
        imageCount.value = currentCount + 1
    }
}