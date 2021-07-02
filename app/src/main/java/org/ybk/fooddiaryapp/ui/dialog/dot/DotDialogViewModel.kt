package org.ybk.fooddiaryapp.ui.dialog.dot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.util.Status
import javax.inject.Inject

class DotDialogViewModel @Inject constructor(
    private val repository: DiaryRepository
): ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status

    fun deleteDiary(diary: Diary) {
        repository.deleteDiary(diary, _status)
    }
}