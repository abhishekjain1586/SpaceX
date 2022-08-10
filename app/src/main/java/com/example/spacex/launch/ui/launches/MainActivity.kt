package com.example.spacex.launch.ui.launches

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.spacex.R
import com.example.spacex.common.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fetchData()
        observeState()
        /*viewModel.getCompany()
        viewModel.getLaunches()*/
    }

    private fun observeState() {
        viewModel.liveDataHomeData.observe(this) { homeUiState ->
            when (homeUiState) {
                is HomeUiState.Loader -> {
                    findViewById<ProgressBar>(R.id.progress_bar).visibility = View.VISIBLE
                }
                is HomeUiState.Success -> {
                    findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                    findViewById<TextView>(R.id.tv_name).text =
                        (homeUiState as HomeUiState.Success).name
                            .plus(" - ")
                            .plus(homeUiState.launches.size)
                }
                is HomeUiState.Failure -> {
                    findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                }
            }
        }

        viewModel.liveDataNavigation.observe(this) { navigationState ->
            when (navigationState) {
                is HomeNavigationState.LaunchDetail -> {
                    startLaunchDetailActivity(navigationState)
                }
            }
        }
    }

    private fun startLaunchDetailActivity(launchDetail: HomeNavigationState.LaunchDetail) {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putSerializable(Constants.PARAM_LAUNCH_LINK_URL, launchDetail.url)
        bundle.putSerializable(Constants.PARAM_LAUNCH_LINK_TYPE, launchDetail.link)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}