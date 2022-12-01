package com.example.domain.usecase.impl

import com.example.core.mapper.Mapper
import com.example.core.model.Resource
import com.example.data.model.CompanyDto
import com.example.data.repository.CompanyRepository
import com.example.domain.model.Company
import com.example.domain.usecase.ErrorMessageUseCase
import com.example.domain.usecase.GetCompanyUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
class GetCompanyUseCaseImpl @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val errorMessageUseCase: ErrorMessageUseCase,
    private val companyMapper: Mapper<CompanyDto, Company>
) : GetCompanyUseCase {

    override fun invoke(companyId: String): Flow<Resource<Company, Int>> {
        return flowOf(
            companyRepository.loadCompany(companyId = companyId)
                .map { throwable -> errorMessageUseCase.invoke(throwable) }
                .map { messageId -> Resource.Error(messageId) }
                .catch { /* should not happen log the exception */ },
            companyRepository.getCompany(companyId = companyId)
                .map { companyDto -> companyMapper.map(companyDto) }
                .map { company -> Resource.Success(company) }
                .catch { /* should not happen log the exception */ }
        ).flattenConcat()
    }
}