package org.ybk.fooddiaryapp.presentation.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.DatabaseResponse
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.domain.usecase.GetOpenDiaryListUseCase
import org.ybk.fooddiaryapp.util.Constants
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val getOpenDiaryListUseCase: GetOpenDiaryListUseCase
) : ViewModel() {

    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList

    fun getOpenDiaryAll() = viewModelScope.launch {
        val response = getOpenDiaryListUseCase.execute(Constants.IS_OPEN)

        when(response) {
            is QueryResponse.Success -> {
                val tempList = ArrayList<Diary>()
                response.snapshot.result.documents.forEach { document ->
                    val diary = document.toObject(Diary::class.java)
                    tempList.add(diary!!)
                }
                tempList.sortByDescending { it.updateTime }
                _diaryList.value = tempList
            }
            is QueryResponse.Failure -> {
                _diaryList.value = arrayListOf()
            }
        }
    }
}