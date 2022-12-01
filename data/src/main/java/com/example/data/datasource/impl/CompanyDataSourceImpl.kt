package com.example.data.datasource.impl

import com.example.core.model.Resource
import com.example.data.api.HittaApi
import com.example.data.datasource.CompanyDataSource
import com.example.data.model.CompanyDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyDataSourceImpl @Inject constructor(
    private val hittaApi: HittaApi
) : CompanyDataSource {

    override suspend fun getCompany(companyId: String): Resource<CompanyDto, Throwable> {
        return try {
            val companyDto = hittaApi.getCompany(companyId).result?.companies?.company?.first()
                ?: throw Throwable("company list is empty")
            Resource.Success(companyDto)
        } catch (throwable: Throwable) {
            Resource.Error(throwable)
        }
    }
}