package com.example.spacex.launch.ui.launches

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.common.network.Resource
import com.example.spacex.launch.domain.GetCompanyDetailUseCase
import com.example.spacex.launch.domain.GetLaunchesUseCase
import com.example.spacex.launch.domain.model.CompanyDetail
import com.example.spacex.launch.domain.model.Launch
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val companyDetailUseCase: GetCompanyDetailUseCase,
    private val launchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    val liveDataHomeData = MutableLiveData<HomeUiState>()
    val liveDataNavigation = MutableLiveData<HomeNavigationState>()

    fun fetchData() {
        liveDataHomeData.value = HomeUiState.Loader
        viewModelScope.launch {
            val companyResFlow = async { companyDetailUseCase() }
            val launchesResFlow = async { launchesUseCase() }
            sendResult(
                companyResult = companyResFlow.await(),
                launchResult = launchesResFlow.await()
            )
        }
    }

    private suspend fun sendResult(
        companyResult: Flow<Resource<CompanyDetail>>,
        launchResult: Flow<Resource<List<Launch>>>
    ) {
        var hasData = false
        var companyDetail: CompanyDetail? = null
        var launches: List<Launch> = listOf()

        companyResult.collect {
            when (it) {
                is Resource.Success -> {
                    hasData = true
                    companyDetail = it.data
                }
                is Resource.Failure -> {}
                is Resource.Error -> {}
            }
        }
        launchResult.collect {
            when (it) {
                is Resource.Success -> {
                    hasData = true
                    launches = it.data
                }
                is Resource.Failure -> {}
                is Resource.Error -> {}
            }
        }
        if (hasData) liveDataHomeData.value = mapToHomeUiState(
            companyDetail = companyDetail,
            launches = launches
        )
        Log.d("testingggg", "test")
    }
}

/*
sealed class HomeUiState {
    class Success(
        val name: String?,
        val founder: String?,
        val founded: Int?,
        val employees: Int?,
        val message: String? = null,
        val launches: List<Launch> = listOf(),
    ) : HomeUiState()

    data class Failure(val message: String) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    object Loader : HomeUiState()
}*/
