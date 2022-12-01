package com.example.data.repository

import com.example.data.model.CompanyDto
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    fun loadCompany(companyId: String): Flow<Throwable>

    fun getCompany(companyId: String): Flow<CompanyDto>
}