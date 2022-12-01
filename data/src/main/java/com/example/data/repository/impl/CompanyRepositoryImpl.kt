package com.example.data.repository.impl

import com.example.core.model.Resource
import com.example.data.datasource.CompanyDataSource
import com.example.data.model.CompanyDto
import com.example.data.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyRepositoryImpl @Inject constructor(
    private val companyDataSource: CompanyDataSource,
) : CompanyRepository {

    private val company = MutableSharedFlow<CompanyDto>(replay = 1)

    override fun loadCompany(companyId: String): Flow<Throwable> {
        return flow {
            val response = companyDataSource.getCompany(companyId)
            when (response) {
                is Resource.Success -> cacheCompany(response.data)
                is Resource.Error -> emit(response.data)
            }
        }
    }

    private suspend fun cacheCompany(companyDto: CompanyDto) {
        company.emit(companyDto)
    }

    override fun getCompany(companyId: String): Flow<CompanyDto> {
        return company
    }
}