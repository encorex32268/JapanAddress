package com.example.japanaddress.domain.mapper

import com.example.japanaddress.data.model.AddressResponse
import com.example.japanaddress.data.model.DetailAddressResponse
import com.example.japanaddress.domain.model.Address
import com.example.japanaddress.domain.model.DetailAddress

fun DetailAddressResponse.toDetailAddress(): DetailAddress {
    return DetailAddress(
        zipcode = zipcode,
        prefectureCode = prefcode,
        prefecture = address1,
        town = address2,
        townArea = address3,
        prefectureName = kana1,
        townName = kana2,
        townAreaName = kana3
    )
}
fun AddressResponse.toAddress(): Address {
    return Address(
        message = message,
        results = results?.map { it.toDetailAddress() },
        status = status
    )
}