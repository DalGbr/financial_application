package com.example.finaldiplom.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finaldiplom.database.entities.Spending
import com.example.finaldiplom.database.SpendingViewModel

@Composable
fun ScrollableSpendingList(date: String, navController: NavController) {
    val spendingViewModel: SpendingViewModel = viewModel()
    val data by spendingViewModel.showSpending(date).observeAsState(emptyList())

    LazyColumn(
    ) {
        items(data) { spending ->
            Spacer(modifier = Modifier.height(10.dp))
            SpendingItem(spending)
        }
    }
}

@Composable
fun SpendingItem(spending: Spending) {
    val spendingViewModel: SpendingViewModel = viewModel()

    Row(
        modifier = Modifier
            .width(350.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFD1D8E0))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Type: ${spending.status} \nAmount: ${spending.money} \n" +
                    "Category: ${spending.category} \nNote: ${spending.note}")
        }

        IconButton(
            onClick = { spendingViewModel.deleteSpending(spending.id) },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete"
            )
        }
    }
}
