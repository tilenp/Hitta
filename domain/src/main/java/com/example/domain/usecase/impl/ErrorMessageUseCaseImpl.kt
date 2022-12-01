package com.example.domain.usecase.impl

import com.example.domain.usecase.ErrorMessageUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import com.example.core.R as CoreR

@Singleton
class ErrorMessageUseCaseImpl @Inject constructor() : ErrorMessageUseCase {

    override fun invoke(throwable: Throwable?): Int {
        return  when (throwable) {
            is IOException -> CoreR.string.Network_not_available
            is HttpException -> getHttpExceptionMessage(throwable)
            else -> CoreR.string.Unknown_error
        }
    }

    private fun getHttpExceptionMessage(httpException: HttpException): Int {
        return when (httpException.code()) {
            in 500..599 -> CoreR.string.Server_error
            else -> CoreR.string.Unknown_error
        }
    }
}