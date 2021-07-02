package org.ybk.fooddiaryapp.ui.editdiary

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.UploadTask
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber
import javax.inject.Inject

class EditDiaryViewModel @Inject constructor(
    private val repository: DiaryRepository
): BaseViewModel() {

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

    fun getOriginalDiary(id: String) {
        repository.getDiary(id).getRemoteResponse()?.let {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val valueEventListener = object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val temp = snapshot.getValue(Diary::class.java)
                            if(temp == null) Timber.d("temp is null")
                            temp?.let {
                                Timber.d("${it.name}")
                                _diary.postValue(it)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
                            Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${error.code}")
                            Timber.e(">>>>>>>>>>>>>>>>> Error Message: ${error.message}")
                        }

                    }
                    it.addValueEventListener(valueEventListener)
                }) {

                }
        }
    }

    fun editDiary(newDiary: Diary) {
        Log.d("TAG", "${newDiary.toString()}")
        repository.deleteDiary(diary.value!!, _status)

        val urls = mutableListOf<String>()
        addDisposable(
            repository.uploadFoodImages(newDiary.imageList)
                .subscribeOn(Schedulers.io())
                .subscribe { uploadTask ->
                    val completeListener = OnCompleteListener<UploadTask.TaskSnapshot> { task ->
                        if(task.isSuccessful) {
                            val downloadUrl = task.result?.storage?.downloadUrl

                            val urlCompleteListener = OnCompleteListener<Uri> { urlTask ->
                                val serverUrl = urlTask.result!!.toString()
                                urls.add(serverUrl)

                                if(urls.size == newDiary.imageList.size) {
                                    addDiaryToDB(newDiary, urls)
                                }
                            }
                            downloadUrl?.addOnCompleteListener(
                                urlCompleteListener
                            )?.addOnFailureListener(
                                failureListener
                            )
                        }
                    }
                    uploadTask.addOnCompleteListener(
                        completeListener
                    ).addOnFailureListener(
                        failureListener
                    )
                }
        )
    }

    private fun addDiaryToDB(diary: Diary, serverUrlArrayList: MutableList<String>) {
        for(index in serverUrlArrayList.indices) {
            imageList.value!![index]
                .serverPath = serverUrlArrayList[index]
        }

        addDisposable(
            repository.addDiary(diary)
                .subscribeOn(Schedulers.io())
                .subscribe { task ->
                    task.addOnCompleteListener {
                        isComplete.postValue(true)
                    }.addOnFailureListener(
                        failureListener
                    )
                })
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