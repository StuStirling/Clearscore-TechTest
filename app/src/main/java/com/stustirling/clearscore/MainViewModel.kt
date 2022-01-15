package com.stustirling.clearscore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stustirling.clearscore.core.ui.ViewState
import com.stustirling.clearscore.model.CreditScoreUiModel
import com.stustirling.clearscore.model.mapper.CreditScoreUiModelMapper
import com.stustirling.clearscore.usecase.GetCreditScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCreditScore: GetCreditScoreUseCase,
    private val creditScoreUiModelMapper: CreditScoreUiModelMapper
) : ViewModel() {

    private val state = MutableLiveData<ViewState<CreditScoreUiModel>>()
    val stateLiveData = state as LiveData<ViewState<CreditScoreUiModel>>

    private val creditScoreRetrievalExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        state.value = ViewState.Error(throwable)
    }

    init {
        fetchCreditScore()
    }

    fun retryCreditScoreRetrieval() {
        fetchCreditScore()
    }

    private fun fetchCreditScore() {
        viewModelScope.launch(creditScoreRetrievalExceptionHandler) {
            state.value = ViewState.Loading
            try {
                val creditScore = getCreditScore()
                state.value =
                    ViewState.Success(creditScoreUiModelMapper.toCreditScoreUiModel(creditScore))
            } catch (e: Exception) {
                state.value = ViewState.Error(e)
            }
        }
    }

}