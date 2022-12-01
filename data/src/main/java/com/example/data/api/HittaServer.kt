package com.example.data.api

interface HittaServerConfig {
    val baseUrl: String
}

object HittaServer {
    val prod = object : HittaServerConfig {
        override val baseUrl: String = PROD_HOST
    }
}

private const val PROD_HOST = "https://api.hitta.se/"