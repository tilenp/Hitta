package com.example.domain.mapper

import com.example.core.mapper.Mapper
import com.example.data.model.CompanyDto
import com.example.data.model.CompanyReviewsDto
import com.example.domain.model.Company
import com.example.domain.model.CompanyReviews
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyMapper @Inject constructor(
    private val companyReviewsMapper: Mapper<CompanyReviewsDto?, CompanyReviews>
): Mapper<CompanyDto, Company> {

    override fun map(objectToMap: CompanyDto): Company {
        return Company(
            id = objectToMap.id.orEmpty(),
            displayName = objectToMap.displayName.orEmpty(),
            reviews = companyReviewsMapper.map(objectToMap.reviews)
        )
    }
}