package com.example.japanaddress.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japanaddress.domain.model.DetailAddress
import com.example.japanaddress.ui.theme.JapanAddressTheme

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: DetailAddress
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "ZipCode: ${address.zipcode}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(text = address.prefecture)
            Text(text = address.town)
            Text(text = address.townArea)

        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun AddressItemPreview() {
    JapanAddressTheme {
        AddressItem(
            address = DetailAddress(
                zipcode = "2503334",
                prefecture = "1234",
                prefectureName = "1234Name",
                prefectureCode = "456",
                town = "town",
                townArea = "townArea",
                townName = "townName",
                townAreaName = "townAreaName"
            )
        )
    }
}