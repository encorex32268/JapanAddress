@file:OptIn(ExperimentalFoundationApi::class)

package com.example.japanaddress.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japanaddress.domain.repository.SearchAddressRepository
import com.example.japanaddress.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class JapanAddressViewModel(
    private val repository: SearchAddressRepository
) : ViewModel(){

    var state by mutableStateOf(JapanAddressState())
        private set

    private val _uiEvent = Channel<JapanAddressEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: JapanAddressAction){
        when(action){
            JapanAddressAction.SearchClick -> {
                viewModelScope.launch {
                    state = state.copy(isSearching = true)
                    val result = repository.searchAddress(state.searchText.text.toString())
                    state = state.copy(isSearching = false)
                    when(result){
                        is Result.Error -> {
                            _uiEvent.send(
                                JapanAddressEvent.ApiError(result.error)
                            )
                        }
                        is Result.Success -> {
                            state = state.copy(
                                items = result.data.results?: emptyList()
                            )
                            if (result.data.status == 400){
                                _uiEvent.send(
                                    JapanAddressEvent.NoDataError
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}