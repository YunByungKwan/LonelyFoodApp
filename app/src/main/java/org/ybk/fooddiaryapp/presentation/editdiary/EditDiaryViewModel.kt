package org.ybk.fooddiaryapp.presentation.editdiary

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.UploadTask
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.data.model.etc.*
import org.ybk.fooddiaryapp.domain.usecase.AddDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.DeleteDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadFoodImageUseCase
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber
import javax.inject.Inject

class EditDiaryViewModel(
    private val getDiaryUseCase: GetDiaryUseCase,
    private val addDiaryUseCase: AddDiaryUseCase,
    private val uploadFoodImageUseCase: UploadFoodImageUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
): ViewModel() {

    private val _diary = MutableLiveData<Diary>()
    val diary: LiveData<Diary> get() = _diary

    val imageList = MutableLiveData<ArrayList<FoodImage>>()

    var imageCount = MutableLiveData<Int>()

    var isComplete = MutableLiveData<Boolean>()

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status

    init {
        imageList.value = ArrayList<FoodImage>()
        imageCount.value = 0
    }

    fun getDiary(email: String, registerTime: String) = viewModelScope.launch {
        val response = getDiaryUseCase.execute(email, registerTime)

        when(response) {
            is DocumentResponse.Success -> {
                val temp = response.snapshot.toObject(Diary::class.java)
                if(temp == null) Timber.d("temp is null")
                temp?.let {
                    Timber.d("${it.name}")
                    _diary.postValue(it)
                }
            }
            is DocumentResponse.Failure -> {
                response.e.printStackTrace()
            }
        }
    }

    fun updateDiary(newDiary: Diary) = viewModelScope.launch {
        val deleteTaskResponse = deleteDiaryUseCase.execute2(diary.value!!)

        when(deleteTaskResponse) {
            is QueryResponse.Success -> {
                val urls = mutableListOf<String>()

                imageList.value?.let { imageList ->
                    imageList.forEachIndexed { index, foodImage ->
                        Log.d("TAG2", "index=$index")
                        val response = uploadFoodImageUseCase.execute(foodImage)

                        when(response) {
                            is UploadResponse.Complete -> {
                                val downloadUrl = response.uri
                                urls.add(downloadUrl)
                                if(index == imageList.size - 1) {
                                    addDiaryToDB(newDiary, urls)
                                }
                            }
                            is UploadResponse.Failure -> {
                                Log.e("TAG2", "Fail.")
                                _status.value = Status.FAILED
                            }
                        }
                    }
                }
            }
            is QueryResponse.Failure -> {
                _status.value = Status.FAILED
            }
        }
    }

    private fun addDiaryToDB(
        diary: Diary, serverUrlArrayList: MutableList<String>
    ) = viewModelScope.launch {
        for(index in serverUrlArrayList.indices) {
            imageList.value!![index].serverPath = serverUrlArrayList[index]
        }

        val response = addDiaryUseCase.execute3(diary)
        when(response) {
            is TaskResponse.Complete -> {
                _status.value = Status.SUCCESS
            }
            is TaskResponse.Failure -> {
                _status.value = Status.FAILED
            }
        }
    }

    fun addImageToView(email: String, uri: Uri) {
        val currentTime = Utils.getCurrentTimeMillis().toString()
        val localUri = uri.toString()
        val tempList = imageList.value
        val id = "${Utils.convertDotToDash(email!!)}-${currentTime}"
        tempList!!.add(FoodImage(id, email, localUri, localUri, currentTime))

        imageList.value = tempList!!
        val currentCount = imageCount.value!!
        imageCount.value = currentCount + 1
    }
}