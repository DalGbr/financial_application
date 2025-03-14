package com.example.finaldiplom.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finaldiplom.database.SpendingViewModel
import com.example.finaldiplom.enums.Screens

@Composable
fun BudgetPreview(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val spendingViewModel: SpendingViewModel = viewModel()
    val budgetAmount by spendingViewModel.getBudget(1).observeAsState(initial = 0f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = String.format("%.2f", budgetAmount) + "€",
            onValueChange = { },
            label = {Text("Balance")},
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = Modifier.weight(1f).padding(start = 5.dp),
            readOnly = true
        )

//        Text(text = "Balance: " + budgetAmount.toString()+"€", fontSize = 25.sp, modifier = Modifier.padding(top = 10.dp))

        Button(
            onClick = { navController.navigate(Screens.addBudget.name) },
            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 15.dp, end = 5.dp)
        ) {
            Text("Add Balance")
        }
    }
}