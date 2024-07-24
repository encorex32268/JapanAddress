package com.example.japanaddress.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressRequest(
    val zipcode: String
)
