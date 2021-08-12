package org.ybk.fooddiaryapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.place.PlaceRes
import org.ybk.fooddiaryapp.domain.usecase.GetPlaceListUseCase
import javax.inject.Inject

class SearchViewModel(
    private val getPlaceListUseCase: GetPlaceListUseCase
    // private val repository: DiaryRepository
): BaseViewModel() {

    private val _searchPlaceResult = MutableLiveData<PlaceRes>()
    val placeRes: LiveData<PlaceRes> get() = _searchPlaceResult

    fun searchPlace2(keyword: String) = viewModelScope.launch {
        val result = getPlaceListUseCase.execute(keyword)
        _searchPlaceResult.postValue(result)
    }

//    fun searchPlace(keyword: String) {
//        addDisposable(
//            repository.searchPlace(keyword)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe ({ result ->
//                    _searchPlaceResult.postValue(result)
//                }) {
//                    val emptyRes = PlaceRes(
//                        "",0,0,
//                        0,"",listOf())
//                    _searchPlaceResult.postValue(emptyRes)
//                })
//    }
}