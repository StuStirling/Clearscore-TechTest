package com.stustirling.clearscore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.stustirling.clearscore.core.ui.ViewState
import com.stustirling.clearscore.model.CreditScore
import com.stustirling.clearscore.model.CreditScoreUiModel
import com.stustirling.clearscore.model.mapper.CreditScoreUiModelMapper
import com.stustirling.clearscore.usecase.GetCreditScoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.*
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val getCreditScoreUseCase: GetCreditScoreUseCase = mock()
    private val creditScoreUiModelMapper: CreditScoreUiModelMapper = mock()

    private val stateObserver: Observer<ViewState<CreditScoreUiModel>> = mock()

    private lateinit var viewModel: MainViewModel

    private fun configureViewModel() {
        viewModel = MainViewModel(getCreditScoreUseCase, creditScoreUiModelMapper)
        viewModel.stateLiveData.observeForever(stateObserver)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        runBlocking {
            whenever(getCreditScoreUseCase.invoke())
                .doSuspendableAnswer {
                    withContext(testDispatcher) {
                        testCreditScore
                    }
                }
        }
        whenever(creditScoreUiModelMapper.toCreditScoreUiModel(testCreditScore))
            .thenReturn(mappedTestCreditScore)
    }

    @After
    fun tearDown() {
        viewModel.stateLiveData.removeObserver(stateObserver)
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit accounts credit score upon launch`() {
        configureViewModel()
        verify(stateObserver, times(1))
            .onChanged(ViewState.Success(mappedTestCreditScore))
    }

    @Test
    fun `should fail due to use case failure`() {
        val throwable = UnknownHostException()
        runBlocking {
            whenever(getCreditScoreUseCase.invoke())
                .doSuspendableAnswer {
                    withContext(testDispatcher) {
                        throw throwable
                    }
                }
        }
        configureViewModel()
        verify(stateObserver, times(1))
            .onChanged(ViewState.Error(throwable))
    }

    private val testCreditScore = CreditScore(
        score = 500,
        maxScoreValue = 900,
        minScoreValue = 0
    )

    private val mappedTestCreditScore = CreditScoreUiModel(
        score = 700,
        maxScoreValue = 1000,
        minScoreValue = 60
    )

}