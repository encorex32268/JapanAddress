package com.example.japanaddress.domain.model


data class Address(
    val message: String?,
    val results: List<DetailAddress>?,
    val status: Int
)
