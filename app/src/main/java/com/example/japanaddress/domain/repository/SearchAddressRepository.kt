package com.example.japanaddress.domain.repository


import com.example.japanaddress.domain.model.Address
import com.example.japanaddress.util.DataError
import com.example.japanaddress.util.Result


interface SearchAddressRepository {
    suspend fun searchAddress(zipCode: String): Result<Address,DataError.Network>
}