package org.ybk.fooddiaryapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val repository: DiaryRepository
) : BaseViewModel() {
    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList

    private val _profileImagePath = MutableLiveData<String>()
    val profileImagePath: LiveData<String> get() = _profileImagePath

    fun getProfileImage(email: String) {
        val valueEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d(">>>>>>>>>>>>>>>>> onDataChange()")
                if(snapshot.value != null) {
                    val hashMap = snapshot.value as HashMap<*, *>
                    val serverPath = hashMap[Constants.IMAGE_SERVER_PATH].toString()
                    Timber.d(">>>>>>>>>>>>>>>>> serverPath: $serverPath")
                    _profileImagePath.value = serverPath
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
                Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${error.code}")
                Timber.e(">>>>>>>>>>>>>>>>> Error Msg: ${error.message}")
            }
        }
        addDisposable(
            repository.getProfileImage(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ref ->
                    ref.addValueEventListener(valueEventListener)
                }) {

                }
        )
    }

    fun getDiaryAll(email: String) {
        val valueEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d(">>>>>>>>>>>>>>>>> onDataChange()")
                val tempList = ArrayList<Diary>()
                snapshot.children.forEach { dataSnapShot ->
                    val temp = dataSnapShot.getValue(Diary::class.java)
                    temp?.let {
                        Timber.d(">>>>>>>>>>>>>>>>>> ${it.name}")
                        tempList.add(it)
                    }
                }
                tempList.sortByDescending { it.updateTime }
                _diaryList.postValue(tempList)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
                Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${error.code}")
                Timber.e(">>>>>>>>>>>>>>>>> Error Message: ${error.message}")
            }
        }
        repository.getDiaryAll(email).getRemoteResponse()?.let {
            addDisposable(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ query ->
                        query.addValueEventListener(valueEventListener)
                    }) {
                        Timber.e(">>>>>>>>>>>>>>>>> Error Message: ${it.message}")
                    }
            )
        }
        repository.getDiaryAll(email).getLocalResponse()?.let {
            addDisposable(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        _diaryList.postValue(list as ArrayList<Diary>?)
                    }) {

                    }
            )
        }
    }

    fun uploadProfileImage(email: String, newPath: String) {
        addDisposable(
            repository.uploadProfileImage(email, newPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { uploadTask ->
                    uploadTask.addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                task.result?.storage?.downloadUrl?.addOnCompleteListener {
                                    val uri = it.result!!
                                    _profileImagePath.postValue(uri.toString())

                                    repository.addProfileImagePath(email, uri.toString())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe { task ->
                                            task.addOnCompleteListener {

                                            }.addOnFailureListener {

                                            }

                                        }
                                }
                            } else {
                            }
                        }
                        .addOnFailureListener {
                        }
                }
        )
    }
}