package com.example.finaldiplom.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.example.finaldiplom.components.graphs.BarChartView
import com.example.finaldiplom.components.DataSource
import com.example.finaldiplom.data_storage.NavigationItem
import com.example.finaldiplom.components.graphs.PieChartView
import com.example.finaldiplom.enums.Screens

@Composable
fun Schemas(navController: NavController){
    val navItemList = listOf(
        NavigationItem("Home", Icons.Default.Home, Screens.main.name),
        NavigationItem("Graphs", Icons.Default.Info, Screens.charts.name)
    )
    var selectedIndex by remember {
        mutableStateOf(1)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(navItem.route){
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
    ) {paddingValues ->
        Column (Modifier.padding(paddingValues)
            .fillMaxSize()
            .background(Color(0xCCB6D0E2)),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(Modifier.padding(15.dp))

            Column(
                Modifier
                    .shadow(8.dp, shape = RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFD1D8E0))
                    .padding(16.dp)){
                Text(text = "Pie Chart of current month: " + DataSource().month)
                PieChartView()
            }

            Spacer(Modifier.padding(10.dp))

            Column(
                Modifier
                    .shadow(8.dp, shape = RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFD1D8E0))
                    .padding(16.dp)
            ) {
                Text(text = "Bar Chart of current year: " + DataSource().year)
                BarChartView()
            }
        }
    }
}