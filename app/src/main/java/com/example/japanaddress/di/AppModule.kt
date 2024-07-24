package com.example.japanaddress.di

import com.example.japanaddress.data.network.HttpClientFactory
import com.example.japanaddress.data.repository.SearchAddressRepositoryImpl
import com.example.japanaddress.domain.repository.SearchAddressRepository
import com.example.japanaddress.presentation.JapanAddressViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory().build()
    }
    singleOf(::SearchAddressRepositoryImpl).bind<SearchAddressRepository>()

    viewModelOf(::JapanAddressViewModel)
}