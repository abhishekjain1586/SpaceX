package com.example.spacex.common.injection

import com.example.spacex.launch.data.LaunchRemoteDataSource
import com.example.spacex.launch.data.LaunchRepository
import com.example.spacex.launch.domain.GetCompanyDetailUseCase
import com.example.spacex.launch.domain.GetLaunchesUseCase
import com.example.spacex.launch.ui.launches.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    viewModel {
        MainActivityViewModel(
            companyDetailUseCase = get(),
            launchesUseCase = get()
        )
    }

    single { GetCompanyDetailUseCase(launchRepository = get()) }
    single { GetLaunchesUseCase(launchRepository = get()) }

    single { LaunchRepository(launchesRemoteDataSource = provideRemoteDataSource(retrofit = get())) }
}

fun provideRemoteDataSource(retrofit: Retrofit): LaunchRemoteDataSource =
    retrofit.create(LaunchRemoteDataSource::class.java)