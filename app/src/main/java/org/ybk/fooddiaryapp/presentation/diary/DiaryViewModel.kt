package org.ybk.fooddiaryapp.presentation.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.util.Constants
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getDiaryListUseCase: GetDiaryListUseCase
) : ViewModel() {

    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList

    fun getDiaryList(email: String) = viewModelScope.launch {
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

    fun sortDiaryListBy(type: Int) {
        val sortedList = ArrayList<Diary>()

        if(type == Constants.SORT_BY_LATEST) {
            _diaryList.value?.sortByDescending { it.updateTime }
        }
        else if(type == Constants.SORT_BY_OLDEST) {
            _diaryList.value?.sortBy { it.updateTime }
        }

        _diaryList.value?.forEach {
            sortedList.add(it)
        }
        _diaryList.value = sortedList
    }
}