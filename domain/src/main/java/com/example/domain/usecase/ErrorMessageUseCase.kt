package com.example.domain.usecase

interface ErrorMessageUseCase {

    operator fun invoke(throwable: Throwable?): Int
}