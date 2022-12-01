package com.example.data.datasource

import com.example.core.model.Resource
import com.example.data.model.CompanyDto

interface CompanyDataSource {

    suspend fun getCompany(companyId: String): Resource<CompanyDto, Throwable>
}