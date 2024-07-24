package com.example.japanaddress.presentation

import com.example.japanaddress.util.DataError

sealed interface JapanAddressEvent {
    data class ApiError(val error: DataError.Network): JapanAddressEvent
    data object NoDataError: JapanAddressEvent
}