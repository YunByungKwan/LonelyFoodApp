package org.ybk.fooddiaryapp.ui.map

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
import timber.log.Timber
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val repository: DiaryRepository
) : BaseViewModel() {

    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList

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
                    }) { error ->
                        Timber.e(">>>>>>>>>>>>>>>>> Error Message: ${error.message}")
                    }
            )
        }
        repository.getDiaryAll(email).getLocalResponse()?.let {
            addDisposable(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        val tempList = ArrayList<Diary>()
                        list.forEach {
                            tempList.add(it)
                        }
                        _diaryList.postValue(tempList)
                    }) {

                    }
            )
        }
    }
}