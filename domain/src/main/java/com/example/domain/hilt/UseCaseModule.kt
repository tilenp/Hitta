package com.example.domain.hilt

import com.example.domain.usecase.*
import com.example.domain.usecase.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @FlowPreview
    @Binds
    abstract fun bindErrorMessageUseCase(errorMessageUseCaseImpl: ErrorMessageUseCaseImpl): ErrorMessageUseCase

    @FlowPreview
    @Binds
    abstract fun bindGetCompanyUseCase(getCompanyUseCaseImpl: GetCompanyUseCaseImpl): GetCompanyUseCase

    @Binds
    abstract fun bindGetMyReviewUseCase(getMyReviewUseCaseImpl: GetMyReviewUseCaseImpl): GetMyReviewUseCase

    @FlowPreview
    @Binds
    abstract fun bindGetReviewsUseCase(getReviewsUseCaseImpl: GetReviewsUseCaseImpl): GetReviewsUseCase

    @Binds
    abstract fun bindSaveReviewUseCase(rateAndReviewUseCaseImpl: SaveReviewUseCaseImpl): SaveReviewUseCase
}