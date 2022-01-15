package com.stustirling.clearscore

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.stustirling.clearscore.core.ui.ViewState
import com.stustirling.clearscore.databinding.ActivityMainBinding
import com.stustirling.clearscore.model.CreditScoreUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureUi()
        observeState()
    }

    private fun configureUi() {
        binding.retry.setOnClickListener {
            viewModel.retryCreditScoreRetrieval()
        }
    }

    private fun observeState() {
        viewModel.stateLiveData.observe(this) { state ->

            binding.loading.isVisible = state == ViewState.Loading
            binding.errorContainer.isVisible = state is ViewState.Error

            if (state is ViewState.Success) {
                updateCreditScore(state.item)
            }
        }
    }

    private fun updateCreditScore(creditScoreUiModel: CreditScoreUiModel) {
        binding.creditScoreDoughnut.isVisible = true
        binding.creditScoreDoughnut.setCreditScore(creditScoreUiModel)
    }
}