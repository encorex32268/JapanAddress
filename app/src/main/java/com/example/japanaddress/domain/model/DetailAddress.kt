package com.example.japanaddress.domain.model

data class DetailAddress(
    val zipcode: String,
    val prefectureCode: String,
    val prefecture: String,
    val town: String,
    val townArea: String,
    val prefectureName: String,
    val townName: String,
    val townAreaName: String,
)
