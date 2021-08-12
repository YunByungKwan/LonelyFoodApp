package org.ybk.fooddiaryapp.presentation.editdiary

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.UploadTask
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber
import javax.inject.Inject

class EditDiaryViewModel: BaseViewModel() {

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

    fun getOriginalDiary2(id: String) = viewModelScope.launch {
//        val result = repository.getDiary2(id)
//
//        if(result is EventResponse.Changed) {
//            val temp = result.snapshot.getValue(Diary::class.java)
//            if(temp == null) Timber.d("temp is null")
//            temp?.let {
//                Timber.d("${it.name}")
//                _diary.postValue(it)
//            }
//        } else if(result is EventResponse.Cancelled) {
//            Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
//            Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${result.error.code}")
//            Timber.e(">>>>>>>>>>>>>>>>> Error Message: ${result.error.message}")
//        }
    }

//    fun getOriginalDiary(id: String) {
//        repository.getDiary(id).getRemoteResponse()?.let {
//            it.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    val valueEventListener = object: ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val temp = snapshot.getValue(Diary::class.java)
//                            if(temp == null) Timber.d("temp is null")
//                            temp?.let {
//                                Timber.d("${it.name}")
//                                _diary.postValue(it)
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
//                            Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${error.code}")
//                            Timber.e(">>>>>>>>>>>>>>>>> Error Message: ${error.message}")
//                        }
//
//                    }
//                    it.addValueEventListener(valueEventListener)
//                }) {
//
//                }
//        }
//    }

    fun editDiary(newDiary: Diary) {
//        Log.d("TAG", "${newDiary.toString()}")
//        repository.deleteDiary(diary.value!!, _status)
//
//        val urls = mutableListOf<String>()
//        addDisposable(
//            repository.uploadFoodImages(newDiary.imageList)
//                .subscribeOn(Schedulers.io())
//                .subscribe { uploadTask ->
//                    val completeListener = OnCompleteListener<UploadTask.TaskSnapshot> { task ->
//                        if(task.isSuccessful) {
//                            val downloadUrl = task.result?.storage?.downloadUrl
//
//                            val urlCompleteListener = OnCompleteListener<Uri> { urlTask ->
//                                val serverUrl = urlTask.result!!.toString()
//                                urls.add(serverUrl)
//
//                                if(urls.size == newDiary.imageList.size) {
//                                    addDiaryToDB(newDiary, urls)
//                                }
//                            }
//                            downloadUrl?.addOnCompleteListener(
//                                urlCompleteListener
//                            )?.addOnFailureListener(
//                                failureListener
//                            )
//                        }
//                    }
//                    uploadTask.addOnCompleteListener(
//                        completeListener
//                    ).addOnFailureListener(
//                        failureListener
//                    )
//                }
//        )
    }

    private fun addDiaryToDB(diary: Diary, serverUrlArrayList: MutableList<String>) {
        for(index in serverUrlArrayList.indices) {
            imageList.value!![index]
                .serverPath = serverUrlArrayList[index]
        }

//        addDisposable(
//            repository.addDiary(diary)
//                .subscribeOn(Schedulers.io())
//                .subscribe { task ->
//                    task.addOnCompleteListener {
//                        isComplete.postValue(true)
//                    }.addOnFailureListener(
//                        failureListener
//                    )
//                })
    }

    private val failureListener = OnFailureListener { e ->
        e.printStackTrace()
        isComplete.postValue(false)
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