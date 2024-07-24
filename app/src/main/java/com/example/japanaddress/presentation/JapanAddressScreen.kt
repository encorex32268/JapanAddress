@file:OptIn(ExperimentalFoundationApi::class)

package com.example.japanaddress.presentation


import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.japanaddress.domain.model.DetailAddress
import com.example.japanaddress.presentation.components.AddressItem
import com.example.japanaddress.ui.theme.JapanAddressTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun JapanAddressScreenRoot(
    viewModel: JapanAddressViewModel = koinViewModel()
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when(event){
            is JapanAddressEvent.ApiError -> {
                Toast.makeText(
                    context,
                    "Error : ${event.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            JapanAddressEvent.NoDataError -> {
                Toast.makeText(
                    context,
                    "No Data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    JapanAddressScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
@Preview(showSystemUi = true)
private fun JapanAddressScreenPreview() {
    JapanAddressTheme {
        JapanAddressScreen(
            state = JapanAddressState(
                searchText = TextFieldState(initialText = "test"),
                isSearching = false,
                items = listOf(
                    DetailAddress(
                        zipcode = "1234",
                        prefectureCode = "456",
                        prefecture = "AA",
                        townName = "BB",
                        townAreaName = "CC",
                        town = "CCC",
                        townArea = "DDDD",
                        prefectureName = ""
                    )
                )
            ),
            onAction = {

            }
        )
    }
}

@Composable
private fun JapanAddressScreen(
    state: JapanAddressState,
    onAction: (JapanAddressAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            BasicTextField2(
                state = state.searchText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                decorator = { innerBox ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ){
                            if (state.searchText.text.isEmpty()){
                                Text(
                                    text = "ex:2403305",
                                    color = Color.LightGray
                                )
                            }
                            innerBox()
                        }
                        if (state.isSearching){
                            Box(
                                modifier = Modifier.size(12.dp),
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator(
                                    color = Color.Black
                                )
                            }
                        }
                    }

                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            IconButton(onClick = {
                onAction(
                    JapanAddressAction.SearchClick
                )
            }) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Search ,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.items){ item ->
                    AddressItem(
                        address = item
                    )
                }
            }
        }

    }


}