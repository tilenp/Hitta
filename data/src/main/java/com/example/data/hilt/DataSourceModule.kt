package com.example.data.hilt

import com.example.data.datasource.CompanyDataSource
import com.example.data.datasource.ReviewsDataSource
import com.example.data.datasource.impl.CompanyDataSourceImpl
import com.example.data.datasource.impl.ReviewsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindCompanyDataSource(companyDataSourceImpl: CompanyDataSourceImpl): CompanyDataSource

    @Binds
    abstract fun bindReviewsDataSource(reviewsDataSourceImpl: ReviewsDataSourceImpl): ReviewsDataSource
}