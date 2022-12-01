package com.example.core.model

sealed class Resource<out Success, out Error> {
    data class Success<out Success>(val data: Success) : Resource<Success, Nothing>()
    data class Error<out Error>(val data: Error) : Resource<Nothing, Error>()
}
