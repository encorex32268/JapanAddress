@file:OptIn(ExperimentalFoundationApi::class)

package com.example.japanaddress.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.example.japanaddress.domain.model.DetailAddress

data class JapanAddressState(
    val items: List<DetailAddress> = emptyList(),
    val searchText: TextFieldState = TextFieldState(),
    val isSearching: Boolean = false
)
