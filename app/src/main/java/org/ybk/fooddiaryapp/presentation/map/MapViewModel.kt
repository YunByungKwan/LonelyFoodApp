package org.ybk.fooddiaryapp.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.DatabaseResponse
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.util.Constants
import timber.log.Timber
import javax.inject.Inject

class MapViewModel(
    private val getDiaryListUseCase: GetDiaryListUseCase
) : BaseViewModel() {

    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList


    fun getDiaryAll2(email: String) = viewModelScope.launch {
        val response = getDiaryListUseCase.execute(email)

        when(response) {
            is QueryResponse.Success -> {
                val newDiaryList = response
                    .snapshot.result.documents
                    .map { document -> document.toObject(Diary::class.java)!! }
                    .sortedByDescending { it.updateTime }
                _diaryList.value = ArrayList(newDiaryList)
            }
            is QueryResponse.Failure -> {
                _diaryList.value = arrayListOf()
            }
        }
    }
}