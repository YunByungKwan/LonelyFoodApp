package org.ybk.fooddiaryapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.data.local.entity.PlaceRes
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: DiaryRepository
): BaseViewModel() {

    private val _searchPlaceResult = MutableLiveData<PlaceRes>()
    val placeRes: LiveData<PlaceRes> get() = _searchPlaceResult

    fun searchPlace(keyword: String) {
        addDisposable(
            repository.searchPlace(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ result ->
                    _searchPlaceResult.postValue(result)
                }) {
                    val emptyRes = PlaceRes(
                        "",0,0,
                        0,"",listOf())
                    _searchPlaceResult.postValue(emptyRes)
                })
    }
}