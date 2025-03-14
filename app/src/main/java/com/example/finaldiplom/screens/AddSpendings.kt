package com.example.finaldiplom.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finaldiplom.components.DataSource
import com.example.finaldiplom.components.DropDownMenu
import com.example.finaldiplom.database.SpendingViewModel
import com.example.finaldiplom.enums.Status
import com.example.finaldiplom.enums.Category
import com.example.finaldiplom.enums.Screens
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DialogForAddingSpendings(date: String, navController: NavController) {
    val year = DataSource().year
    val spendingViewModel: SpendingViewModel = viewModel()
    val context = LocalContext.current

    var selectedStatus by remember { mutableStateOf<Status?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var remark by remember { mutableStateOf("") }
    var moneyAmount by remember { mutableStateOf("") }
    val maxChar = 50

    val parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
    val month = parsedDate.month

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(400.dp)
            .padding(top = 60.dp)
            .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Gray)
    )

    Box(Modifier.background(Color(0xCCB6D0E2))){
        Row(Modifier.padding(top = 20.dp)) {
            IconButton(onClick = { navController.navigate(Screens.main.name) }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                )
            }
        }

        Box(modifier = Modifier.padding(horizontal = 30.dp)) {
            Row(
                Modifier.fillMaxWidth().padding(top = 80.dp)
            ) {
                Text("Status", modifier = Modifier.padding(top = 25.dp, end = 20.dp))
                DropDownMenu(enumClass = Status::class.java,
                    selectedEnum = selectedStatus,
                    onEnumSelected = { selectedStatus = it })
            }


            Row(
                modifier = Modifier.padding(top = 150.dp, end = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Money amount", modifier = Modifier.padding(top = 25.dp, end = 5.dp))
                OutlinedTextField(
                    value = moneyAmount,
                    onValueChange = {
                        if (it.isEmpty() || it.toFloatOrNull() != null) {
                            moneyAmount = it
                        }
                    },
                    label = { Text("") }
                )
            }

            Row(
                Modifier.padding(top = 230.dp), horizontalArrangement = Arrangement.Center
            ) {
                Text("Category", modifier = Modifier.padding(top = 30.dp, end = 5.dp))
                DropDownMenu(enumClass = Category::class.java,
                    selectedEnum = selectedCategory,
                    onEnumSelected = { selectedCategory = it })
            }

            Row(
                Modifier.padding(top = 300.dp), horizontalArrangement = Arrangement.Center
            ) {
                Text("Note", modifier = Modifier.padding(top = 20.dp, end = 5.dp))
                OutlinedTextField(
                    value = remark,
                    onValueChange = {
                        remark = it
                        if (it.length >= maxChar) {
                            remark = it
                        }
                    },
                    label = { Text("") }, modifier = Modifier.padding(start = 40.dp)
                )
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(top = 400.dp),
                horizontalArrangement = Arrangement.End
            ) {
                val budgetAmount by spendingViewModel.getBudget(1).observeAsState(initial = 0f)

                Button(onClick = {
                    if (selectedStatus != null && moneyAmount.isNotEmpty() && selectedCategory != null && remark.isNotEmpty()) {

                        spendingViewModel.addSpending(
                            date = date,
                            month = month.toString(),
                            year = year,
                            status = selectedStatus,
                            money = moneyAmount,
                            category = selectedCategory,
                            note = remark
                        )

                        if (selectedStatus.toString() == "OUTCOME") {
                            val newBalance = budgetAmount - moneyAmount.toFloat()
                            spendingViewModel.updateBudget(newBalance, 1)
                        } else {
                            val newBalance = budgetAmount + moneyAmount.toFloat()
                            spendingViewModel.updateBudget(newBalance, 1)
                        }

                        navController.navigate(Screens.main.name)
                        Toast.makeText(context, "Record has been added successfully", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(context, "All fields must be filled", Toast.LENGTH_LONG).show()
                    }

                }) {
                    Text(text = "Add spending")
                }
            }
        }
    }
}

