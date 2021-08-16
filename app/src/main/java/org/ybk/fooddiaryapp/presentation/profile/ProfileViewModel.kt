package org.ybk.fooddiaryapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.DatabaseResponse
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import org.ybk.fooddiaryapp.domain.usecase.AddProfileImagePathUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetProfileImageUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadProfileImageUseCase
import org.ybk.fooddiaryapp.util.Constants
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getDiaryListUseCase: GetDiaryListUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val getProfileImageUseCase: GetProfileImageUseCase,
    private val addProfileImagePathUseCase: AddProfileImagePathUseCase
) : BaseViewModel() {

    private val _diaryList = MutableLiveData<ArrayList<Diary>>()
    val diaryList: LiveData<ArrayList<Diary>> get() = _diaryList

    private val _profileImagePath = MutableLiveData<String>()
    val profileImagePath: LiveData<String> get() = _profileImagePath

    fun getProfileImage(email: String) = viewModelScope.launch {
        val data = getProfileImageUseCase.execute(email)

        if(data is ValueEventResponse.Changed) {
            Timber.d(">>>>>>>>>>>>>>>>> onDataChange()")
            if(data.snapshot.value != null) {
                val hashMap = data.snapshot.value as HashMap<*, *>
                val serverPath = hashMap[Constants.IMAGE_SERVER_PATH].toString()
                Timber.d(">>>>>>>>>>>>>>>>> serverPath: $serverPath")
                _profileImagePath.value = serverPath
            }
        } else if(data is ValueEventResponse.Cancelled) {
            Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
            Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${data.error.code}")
            Timber.e(">>>>>>>>>>>>>>>>> Error Msg: ${data.error.message}")
        }

//        val valueEventListener = object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Timber.d(">>>>>>>>>>>>>>>>> onDataChange()")
//                if(snapshot.value != null) {
//                    val hashMap = snapshot.value as HashMap<*, *>
//                    val serverPath = hashMap[Constants.IMAGE_SERVER_PATH].toString()
//                    Timber.d(">>>>>>>>>>>>>>>>> serverPath: $serverPath")
//                    _profileImagePath.value = serverPath
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Timber.e(">>>>>>>>>>>>>>>>> onCancelled()")
//                Timber.e(">>>>>>>>>>>>>>>>> Error Code: ${error.code}")
//                Timber.e(">>>>>>>>>>>>>>>>> Error Msg: ${error.message}")
//            }
//        }
//        addDisposable(
//            getProfileImageUseCase.execute(email)
//            // repository.getProfileImage(email)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ ref ->
//                    ref.addValueEventListener(valueEventListener)
//                }) {
//
//                }
//        )
    }

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

    fun uploadProfileImage(email: String, newPath: String) {
        addDisposable(
            uploadProfileImageUseCase.execute(email, newPath)
            // repository.uploadProfileImage(email, newPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { uploadTask ->
                    uploadTask.addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                task.result?.storage?.downloadUrl?.addOnCompleteListener {
                                    val uri = it.result!!
                                    _profileImagePath.postValue(uri.toString())

                                    addProfileImagePathUseCase.execute(email, uri.toString())
                                    // repository.addProfileImagePath(email, uri.toString())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe { task ->
                                            task.addOnCompleteListener {

                                            }.addOnFailureListener {

                                            }

                                        }
                                }
                            } else {
                            }
                        }
                        .addOnFailureListener {
                        }
                }
        )
    }
}