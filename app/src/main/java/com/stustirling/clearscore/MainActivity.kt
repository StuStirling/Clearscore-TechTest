package com.stustirling.clearscore

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.stustirling.clearscore.core.ui.ViewState
import com.stustirling.clearscore.databinding.ActivityMainBinding
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
    }

    private fun observeState() {
        viewModel.stateLiveData.observe(this) { state ->
            binding.loading.visibility = if (state == ViewState.Loading) View.VISIBLE else View.GONE

            if (state is ViewState.Error) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.hosts_retrieval_error_title)
                    .setMessage(R.string.hosts_retrieval_error_content)
                    .setPositiveButton(
                        R.string.hosts_retrieval_error_positive
                    ) { _, _ -> viewModel.retryCreditScoreRetrieval() }
                    .setCancelable(false)
                    .show()
            }
        }
    }
}