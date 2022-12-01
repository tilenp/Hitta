package com.example.domain.hilt

import com.example.core.mapper.Mapper
import com.example.data.model.CompanyDto
import com.example.data.model.CompanyReviewsDto
import com.example.data.model.ReviewBodyDto
import com.example.data.model.ReviewDto
import com.example.domain.mapper.*
import com.example.domain.model.Company
import com.example.domain.model.CompanyReviews
import com.example.domain.model.Review
import com.example.core.model.ReviewBody
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MapperModule {

    @Binds
    abstract fun bindCompanyMapper(companyMapper: CompanyMapper): Mapper<CompanyDto, Company>

    @Binds
    abstract fun bindCompanyReviewMapper(companyReviewMapper: CompanyReviewMapper): Mapper<CompanyReviewsDto?, CompanyReviews>

    @Binds
    abstract fun bindReviewMapper(reviewMapper: ReviewMapper): Mapper<ReviewDto, Review>

    @Binds
    abstract fun bindReviewBodyDtoMapper(reviewBodyDtoMapper: ReviewBodyDtoMapper): Mapper<ReviewBody, ReviewBodyDto>
}

