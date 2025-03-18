package com.example.finaldiplom


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.finaldiplom.components.ScrollableSpendingList
import com.example.finaldiplom.data_storage.BudgetViewModel
import com.example.finaldiplom.database.SpendingDatabase
import com.example.finaldiplom.database.SpendingDatabase.Companion.MIGRATION_2_3
import com.example.finaldiplom.database.SpendingViewModel
import com.example.finaldiplom.enums.Screens
import com.example.finaldiplom.ui.theme.FinalDiplomTheme
import com.example.finaldiplom.screens.BudgetApplication
import com.example.finaldiplom.screens.DialogForAddingBudget
import com.example.finaldiplom.screens.DialogForAddingSpendings
import com.example.finaldiplom.screens.Schemas

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var spendingDatabase: SpendingDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spendingDatabase = Room.databaseBuilder(
            applicationContext,
            SpendingDatabase::class.java,
            SpendingDatabase.NAME
        )
            .addMigrations(MIGRATION_2_3)
            .build()

        val spendingViewModel: SpendingViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        spendingViewModel.checkAndCreateBudget() // Проверка и создание бюджета с id = 1

        enableEdgeToEdge()
        setContent {
            FinalDiplomTheme {
                val navController = rememberNavController()
                val budgetViewModel: BudgetViewModel = viewModel()
                NavHost(navController = navController, startDestination = Screens.main.name) {
                    composable(Screens.main.name) {
                        BudgetApplication(navController, budgetViewModel)
                    }
                    composable(Screens.charts.name) {
                        Schemas(navController)
                    }
                    composable(Screens.addBudget.name) {
                        DialogForAddingBudget(navController, budgetViewModel)
                    }
                    composable(Screens.addSpending.name + "/{date}") { backStackEntry ->
                        val takenDate = backStackEntry.arguments?.getString("date")
                        val formattedDate = takenDate.toString()
                        DialogForAddingSpendings(formattedDate, navController)
                    }
                    composable(Screens.showSpendings.name + "/{date}") { backStackEntry ->
                        val takenDate = backStackEntry.arguments?.getString("date")
                        val formattedDate = takenDate.toString()
                        ScrollableSpendingList(formattedDate, navController)
                    }
                }
            }
        }
    }
}






