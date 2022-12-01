package com.example.data.api

import com.example.data.model.HittaResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HittaApi {

    @GET("search/v7/app/company/{companyId}")
    suspend fun getCompany(
        @Path("companyId") companyId: String,
    ): HittaResponseDto
}
