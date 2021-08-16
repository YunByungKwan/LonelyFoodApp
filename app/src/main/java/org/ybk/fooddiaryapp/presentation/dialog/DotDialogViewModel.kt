package org.ybk.fooddiaryapp.presentation.dialog

import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.data.model.etc.TaskResponse
import org.ybk.fooddiaryapp.domain.usecase.*
import org.ybk.fooddiaryapp.util.Status
import javax.inject.Inject

@HiltViewModel
class DotDialogViewModel @Inject constructor(
    private val recommendRestaurantUseCase: RecommendRestaurantUseCase,
    private val updateDiaryToOpenUseCase: UpdateDiaryToOpenUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
): ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status

    fun deleteDiary(diary: Diary) {
        deleteDiaryUseCase.execute(diary, _status)
    }

    fun deleteDiary2(diary: Diary) = CoroutineScope(Dispatchers.IO).launch {
        Log.d("TAG2", "deleteDiary2")
        val response = deleteDiaryUseCase.execute2(diary)
        when(response) {
            is QueryResponse.Success -> {
                Log.d("TAG2", "DELETE_SUCCESS")
                _status.postValue(Status.DELETE_SUCCESS)
            }
            is QueryResponse.Failure -> {
                Log.d("TAG2", "DELETE_FAILED")
                _status.postValue(Status.DELETE_FAILED)
            }
        }
    }

    fun recommendRestaurantToOthers(diary: Diary) = CoroutineScope(Dispatchers.IO).launch {
        Log.d("TAG2", "DotDialogViewModel - recommendRestaurantToOthers")
        val response = recommendRestaurantUseCase.execute(diary)

        when(response) {
            is TaskResponse.Complete -> {
                val res = updateDiaryToOpenUseCase.execute(diary)
                when(res) {
                    is TaskResponse.Complete -> {
                        _status.postValue(Status.RECOMM_SUCCESS)
                    }
                    is TaskResponse.Failure -> {
                        _status.postValue(Status.RECOMM_FAILED)
                    }
                }
            }
            is TaskResponse.Failure -> {
                _status.postValue(Status.RECOMM_FAILED)
            }
        }
    }
}