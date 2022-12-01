package com.example.data.api

interface TestHittaServerConfig {
    val baseUrl: String
}

object TestHittaServer {
    val prod = object : TestHittaServerConfig {
        override val baseUrl: String = PROD_HOST
    }
}

private const val PROD_HOST = "https://test.hitta.se/"