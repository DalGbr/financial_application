package com.example.finaldiplom.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finaldiplom.MainActivity
import com.example.finaldiplom.data_storage.BudgetViewModel
import com.example.finaldiplom.database.SpendingViewModel
import com.example.finaldiplom.enums.Screens

@Composable
fun DialogForAddingBudget(navController: NavController, budgetViewModel: BudgetViewModel) {
    var amount by remember { mutableStateOf(budgetViewModel.amount) }
    val spendingViewModel: SpendingViewModel = viewModel()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(300.dp)
            .padding(top = 90.dp)
            .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Gray)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color(0xCCB6D0E2)),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate(Screens.main.name) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                    )
                }

                Text(
                    text = "Adding budget", modifier = Modifier.padding(start = 90.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(text = "Amount") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (amount.isNotEmpty() && amount != "0") {
                    spendingViewModel.updateBudget(amount.toFloat(), 1)
                    navController.navigate(Screens.main.name)
                } else {
                    Toast.makeText(context, "You must insert your budget to add it", Toast.LENGTH_LONG).show()
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add")
            }
        }
    }
}