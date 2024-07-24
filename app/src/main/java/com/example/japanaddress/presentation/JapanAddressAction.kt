package com.example.japanaddress.presentation

sealed interface JapanAddressAction {
    data object SearchClick: JapanAddressAction
}