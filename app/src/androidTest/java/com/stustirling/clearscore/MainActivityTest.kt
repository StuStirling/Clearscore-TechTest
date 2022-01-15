package com.stustirling.clearscore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.stustirling.clearscore.core.ui.ViewState
import com.stustirling.clearscore.model.CreditScoreUiModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule(order = 2)
    val rule: TestRule = InstantTaskExecutorRule()

    private val state = MutableLiveData<ViewState<CreditScoreUiModel>>()

    @BindValue
    val mainViewModel: MainViewModel = mockkClass(relaxed = true, type = MainViewModel::class)
        .apply { every { stateLiveData } returns state }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun shouldShowLoading() {
        onView(withId(R.id.loading))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowAnError() {
        activityRule.scenario.onActivity {
            state.value = ViewState.Error()
        }
        onView(withText(R.string.credit_score_retrieval_error_content))
            .check(matches(isDisplayed()))
        onView(withText(R.string.credit_score_retrieval_error_positive))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldAttemptRetryOnButtonPressAfterError() {
        activityRule.scenario.onActivity {
            state.value = ViewState.Error()
        }

        onView(withId(R.id.retry))
            .perform(click())

        verify { mainViewModel.retryCreditScoreRetrieval() }
    }

    @Test
    fun shouldShowLoadingAgainAfterRetryAndDismissError() {
        activityRule.scenario.onActivity {
            state.value = ViewState.Error()
        }

        onView(withText(R.string.credit_score_retrieval_error_content))
            .check(matches(isDisplayed()))

        activityRule.scenario.onActivity {
            state.value = ViewState.Loading
        }

        onView(withId(R.id.loading))
            .check(matches(isDisplayed()))
    }

    private fun emitSuccessState() {
        activityRule.scenario.onActivity {
            state.value = ViewState.Success(
                CreditScoreUiModel(
                    score = 500,
                    maxScoreValue = 700,
                    minScoreValue = 200
                )
            )
        }
    }

    @Test
    fun shouldShowDoughnutViewWhenCreditScoreRetrieved() {
        emitSuccessState()

        onView(withId(R.id.credit_score_doughnut))
            .check(matches(isDisplayed()))
        onView(withId(R.id.loading))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.error_container))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldShowTheCorrectScoreInDoughnutView() {
        emitSuccessState()

        onView(withId(R.id.score))
            .check(matches(withText("500")))
    }

    @Test
    fun shouldShowTheCorrectMaxScoreInDoughnutView() {
        emitSuccessState()

        onView(withId(R.id.max))
            .check(matches(withText(containsString("700"))))
    }
}