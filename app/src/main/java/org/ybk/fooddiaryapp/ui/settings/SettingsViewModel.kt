package org.ybk.fooddiaryapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.util.Status
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val repository: DiaryRepository
): BaseViewModel() {

    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status

    fun deleteDiaryAll(email: String) {
        Timber.d(">>>>>>>>>>>>>>>>> [SettingsViewModel] deleteDiaryAll()")
        val valueEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d(">>>>>>>>>>>>>>>>> onDataChange()")
                if(snapshot.children.count() == 0) {
                    Timber.d(">>>>>>>>>>>>>>>>> count:${snapshot.children.count()}")
                    _status.postValue(Status.DELETE_SUCCESS)
                    return
                }

                snapshot.children.forEach { dataSnapShot ->
                    val diary = dataSnapShot.getValue(Diary::class.java)
                    diary?.let {
                        repository.deleteDiary(it, _status)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
                Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${error.code}")
                Timber.e(">>>>>>>>>>>>>>>>> Error Message: ${error.message}")
            }

        }
        repository.getDiaryAll(email).getRemoteResponse()?.let {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ query ->
                    query.addValueEventListener(valueEventListener)
                }) {
                    Timber.e(">>>>>>>>>>>>>>>>> ${it.message}")
                }
        }
    }

    fun withdrawFirebase() {
        addDisposable(
            repository.withdrawFirebase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ task ->
                    task.addOnCompleteListener {
                        Timber.d("addOnCompleteListener")
                        _status.postValue(Status.WITHDRAW_SUCCESS)
                    }.addOnFailureListener {
                        Timber.d("addOnFailureListener")
                        Timber.d(">>>>>>>>>>>>>>>> ${it.message}")
                        _status.postValue(Status.WITHDRAW_FAILED)
                    }
                }){
                    Timber.d(">>>>>>>>>>>>>>>> ${it.message}")
                    _status.postValue(Status.WITHDRAW_FAILED)
                }
        )
    }
}