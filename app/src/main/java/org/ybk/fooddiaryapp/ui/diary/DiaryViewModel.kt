package org.ybk.fooddiaryapp.ui.diary

import android.util.Log
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
import timber.log.Timber
import javax.inject.Inject

class DiaryViewModel @Inject constructor(
    private val repository: DiaryRepository
) : BaseViewModel() {

    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList



    fun getDiaryAll(email: String) {
        val valueEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = ArrayList<Diary>()
                snapshot.children.forEach { dataSnapShot ->
                    dataSnapShot.getValue(Diary::class.java)?.let { diary ->
                        tempList.add(diary)
                        insertDiaryToLocalDB(diary)
                    }
                }
                tempList.sortByDescending { it.updateTime }
                _diaryList.postValue(tempList)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.tag(Constants.TAG).e("==> onCancelled()")
                Timber.tag(Constants.TAG).e("==> ${error.message}")
            }
        }
        repository.getDiaryAll(email).getRemoteResponse()?.let {
            addDisposable(it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({query ->
                    query.addValueEventListener(valueEventListener)
                }) {
                    Timber.tag(Constants.TAG).e("==> ${it.message}")
                })
        }
        repository.getDiaryAll(email).getLocalResponse()?.let {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    Timber.d("DiaryList from Room: ${list.size}")
                    list.forEach {
                        Timber.d("${it.contents}")
                    }
                    _diaryList.postValue(list as ArrayList<Diary>?)
                }) {

                }
        }
    }

    fun insertDiaryToLocalDB(diary: Diary) {
        repository.insertDiaryToLocalDB(diary)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun sortDiaryListBy(type: Int) {
        val sortedList = ArrayList<Diary>()
        if(type == Constants.SORT_BY_LATEST) {
            _diaryList.value?.sortByDescending { it.updateTime }
        } else if(type == Constants.SORT_BY_OLDEST) {
            _diaryList.value?.sortBy { it.updateTime }
        }
        _diaryList.value?.forEach {
            sortedList.add(it)
        }
        _diaryList.postValue(sortedList)
    }
}