package com.theblackbit.instantflix.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theblackbit.instantflix.core.di.IoDispatcher
import com.theblackbit.instantflix.core.usecase.GenresHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO: ADD UNIT TEST
@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val genresHandlerUseCase: GenresHandlerUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @IoDispatcher private val mainDispatcher: CoroutineDispatcher,

) : ViewModel() {

    private val _keepSplashFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val keepSplashFlow: StateFlow<Boolean> = _keepSplashFlow

    init {
        viewModelScope.launch(ioDispatcher) {
            genresHandlerUseCase.storeGenres()
            withContext(mainDispatcher) {
                _keepSplashFlow.emit(false)
            }
        }
    }
}
