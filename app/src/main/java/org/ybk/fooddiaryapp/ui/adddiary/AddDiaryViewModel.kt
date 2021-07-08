package org.ybk.fooddiaryapp.ui.adddiary

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.airbnb.lottie.L
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber
import javax.inject.Inject

class AddDiaryViewModel @Inject constructor(
    private val repository: DiaryRepository
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

    fun addDiary(email: String) {
        val urls = mutableListOf<String>()
        //
        val startTime = System.currentTimeMillis()
        //
        addDisposable(
            repository.uploadFoodImages(viewImageList.value!!)
                .subscribeOn(Schedulers.io())
                .subscribe { uploadTask ->
                    val completeListener = OnCompleteListener<UploadTask.TaskSnapshot> { task ->
                        if(task.isSuccessful) {
                            val downloadUrl = task.result?.storage?.downloadUrl

                            val urlCompleteListener = OnCompleteListener<Uri> { urlTask ->
                                val serverUrl = urlTask.result!!.toString()
                                val endTime = System.currentTimeMillis()
                                Log.d("TAG", "개별 걸린 시간:${endTime - startTime}(시작: $startTime, 끝: $endTime)")
                                urls.add(serverUrl)
                                if(urls.size == viewImageList.value!!.size) {
                                    val endTime = System.currentTimeMillis()
                                    Log.d("TAG", " 총 걸린 시간:${endTime - startTime}(시작: $startTime, 끝: $endTime)")
                                    addDiaryToDB(email, urls)
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

    private fun addDiaryToDB(email: String, serverUrlArrayList: MutableList<String>) {
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

        addDisposable(
            repository.addDiary(diary)
            .subscribeOn(Schedulers.io())
            .subscribe { task ->
                task.addOnCompleteListener {
                    status.postValue(Status.SUCCESS)
                }.addOnFailureListener(
                    failureListener
                )
            })
    }

    private val failureListener = OnFailureListener { e ->
        e.printStackTrace()
        status.postValue(Status.FAILED)
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