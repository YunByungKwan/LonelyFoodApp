package org.ybk.fooddiaryapp.presentation.search

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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.domain.usecase.GetPlaceListUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getPlaceListUseCase: GetPlaceListUseCase,
    private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _placeResponse = MutableLiveData<PlaceResponse>()
    val placeResponse: LiveData<PlaceResponse> get() = _placeResponse

    fun searchPlace(keyword: String) = viewModelScope.launch(defaultDispatcher) {
        val result = getPlaceListUseCase.execute(keyword)
            .apply {
            items.map { place ->
                place.title = place.title
                    .replace("<b>", "")
                    .replace("</b>", "")
            }
        }
        _placeResponse.value = result
    }
}