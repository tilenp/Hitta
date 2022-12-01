package com.example.data.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestHittaApi {

    @Headers(
        "X-HITTA-DEVICE-NAME: MOBILE_WEB",
        "X-HITTA-SHARED-IDENTIFIER: 15188693697264027"
    )
    @POST("reviews/v1/company")
    @FormUrlEncoded
    suspend fun saveReview(
        @Field("score") score: Int,
        @Field("companyId") companyId: String,
        @Field("comment") comment: String,
        @Field("userName") userName: String,
    ): Response<Unit>
}