package com.example.domain.usecase

import com.example.core.model.Resource
import com.example.domain.model.Company
import kotlinx.coroutines.flow.Flow

interface GetCompanyUseCase {

    operator fun invoke(companyId: String): Flow<Resource<Company, Int>>
}