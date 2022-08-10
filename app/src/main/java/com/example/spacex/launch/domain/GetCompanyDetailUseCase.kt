package com.example.spacex.launch.domain

import com.example.spacex.launch.data.LaunchRepository

class GetCompanyDetailUseCase(private val launchRepository: LaunchRepository) {

    suspend operator fun invoke() = launchRepository.getCompanyDetail()
}