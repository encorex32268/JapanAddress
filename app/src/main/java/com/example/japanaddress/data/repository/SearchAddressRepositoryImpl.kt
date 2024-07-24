package com.example.japanaddress.data.repository

import com.example.japanaddress.data.model.AddressResponse
import com.example.japanaddress.data.model.DetailAddressResponse
import com.example.japanaddress.data.network.get
import com.example.japanaddress.domain.mapper.toAddress
import com.example.japanaddress.domain.mapper.toDetailAddress
import com.example.japanaddress.domain.model.Address
import com.example.japanaddress.domain.model.DetailAddress
import com.example.japanaddress.domain.repository.SearchAddressRepository
import com.example.japanaddress.util.DataError
import com.example.japanaddress.util.Result
import com.example.japanaddress.util.map
import io.ktor.client.HttpClient

class SearchAddressRepositoryImpl(
    private val httpClient: HttpClient
): SearchAddressRepository {

    override suspend fun searchAddress(zipCode: String): Result<Address, DataError.Network> {
        return httpClient.get<AddressResponse>(
            route = "/search",
            queryParameters = mapOf(
                "zipcode" to zipCode
            )
        ).map {
            it.toAddress()
        }
    }
}