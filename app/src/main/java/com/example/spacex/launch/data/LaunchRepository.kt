package com.example.spacex.launch.data

import com.example.spacex.common.data.repository.BaseRepository
import com.example.spacex.common.network.Resource
import com.example.spacex.launch.domain.model.CompanyDetail
import kotlinx.coroutines.flow.Flow

class LaunchRepository(
    private val launchesRemoteDataSource: LaunchRemoteDataSource,
) : BaseRepository() {

    suspend fun getCompanyDetail(): Flow<Resource<CompanyDetail>> = toResourceFlow(
        response = launchesRemoteDataSource.getCompanyDetail(),
        transform = LaunchMapper::mapToCompanyDetail,
    )

    suspend fun getLaunches() = toResourceFlow(
        response = launchesRemoteDataSource.getLaunches(),
        transform = LaunchMapper::mapToLaunches,
    )
}