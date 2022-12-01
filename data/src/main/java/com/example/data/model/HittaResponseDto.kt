package com.example.data.model

data class HittaResponseDto(
    val result: HittaResultDto? = null,
)

data class HittaResultDto(
    val companies: HittaCompaniesDto? = null,
)

data class HittaCompaniesDto(
    val company: List<CompanyDto>? = null,
)

data class CompanyDto(
    val id: String? = null,
    val displayName: String? = null,
    val reviews: CompanyReviewsDto? = null,
)

data class CompanyReviewsDto(
    val score: Double? = null,
    val count: Long? = null,
)
