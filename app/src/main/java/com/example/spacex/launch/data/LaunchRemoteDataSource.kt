package com.example.spacex.launch.data

import com.example.spacex.launch.data.model.CompanyDetailDto
import com.example.spacex.launch.data.model.LaunchDto
import retrofit2.Response
import retrofit2.http.GET

interface LaunchRemoteDataSource {
    @GET("v3/info")
    suspend fun getCompanyDetail(): Response<CompanyDetailDto>

    @GET("v3/launches")
    suspend fun getLaunches(): Response<List<LaunchDto>>
}