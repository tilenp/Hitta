package com.example.data.hilt

import com.example.data.repository.CompanyRepository
import com.example.data.repository.ReviewsRepository
import com.example.data.repository.impl.CompanyRepositoryImpl
import com.example.data.repository.impl.ReviewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCompanyRepository(companyRepositoryImpl: CompanyRepositoryImpl): CompanyRepository

    @Binds
    abstract fun bindReviewsRepository(reviewsRepositoryImpl: ReviewsRepositoryImpl): ReviewsRepository
}