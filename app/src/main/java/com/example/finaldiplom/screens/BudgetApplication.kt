package com.example.finaldiplom.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finaldiplom.components.CalendarPreview
import com.example.finaldiplom.components.BudgetPreview
import com.example.finaldiplom.components.CalendarData
import com.example.finaldiplom.data_storage.NavigationItem
import com.example.finaldiplom.components.ScrollableSpendingList
import com.example.finaldiplom.data_storage.BudgetViewModel
import com.example.finaldiplom.enums.Screens

@Composable
fun BudgetApplication(navController: NavController, budgetViewModel: BudgetViewModel) {
    val navItemList = listOf(
        NavigationItem("Home", Icons.Default.Home, Screens.main.name),
        NavigationItem("Graphs", Icons.Default.Info, Screens.charts.name)
    )
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    var selectedDate: CalendarData.Date? by remember { mutableStateOf(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(navItem.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = navItem.icon, contentDescription = "Icon") },
                        label = { Text(text = navItem.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xCCB6D0E2))
        ) {

            ///для верха
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(300.dp)
                    .padding(top = 25.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFA7C7E7))
            )


            ///для  календаря
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .height(270.dp)
                    .padding(top = 140.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFD1D8E0))
            )

            ///для бюджета
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .height(130.dp)
                    .padding(top = 35.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFD1D8E0))
            )

            Row(
                modifier = Modifier.padding(top = 40.dp)
            ) {
                BudgetPreview(
                    modifier = Modifier
                        .width(400.dp)
                        .padding(start = 15.dp),
                    navController = navController
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 90.dp)
            ) {
                CalendarPreview(
                    navController,
                    onDateSelected = { date ->
                        selectedDate = date
                    })
            }

            Text(text = "Records:", Modifier.fillMaxSize().padding(start = 13.dp, top = 320.dp), fontSize = 20.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
                    .height(750.dp)
                    .padding(top = 350.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFA7C7E7)) // Используем полупрозрачный фон (80% непрозрачности)
            )

            if (selectedDate != null) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 350.dp, bottom = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ScrollableSpendingList(
                        date = selectedDate!!.date.toString(),
                        navController = navController
                    )
                }
            }
        }
    }
}
