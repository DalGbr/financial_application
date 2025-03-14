package com.example.finaldiplom.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaldiplom.MainActivity
import com.example.finaldiplom.data_storage.SpendingSummary
import com.example.finaldiplom.data_storage.YearSpendingSummary
import com.example.finaldiplom.database.entities.Budget
import com.example.finaldiplom.database.entities.Spending
import com.example.finaldiplom.enums.Category
import com.example.finaldiplom.enums.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpendingViewModel(): ViewModel() {
    val spendingDao = MainActivity.spendingDatabase.getSpendingDao()

    fun addSpending(date: String, month: String, year: String, status: Status?, money: String, category: Category?, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            spendingDao.addSpending(
                Spending(
                    date = date, month = month, year = year, status = status, money = money, category = category, note = note
                )
            )
        }
    }

    fun showSpending(date: String): LiveData<List<Spending>>{
        return spendingDao.getPreciseSpending(date)
    }

    fun deleteSpending(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            spendingDao.deleteSpending(id)
        }
    }

    fun getBudget(id: Int): LiveData<Float>{
         return spendingDao.getBudget(id)

    }

    fun updateBudget(amount: Float, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            spendingDao.updateBudget(amount, id)
        }
    }

    fun getSpendingByData(month: String, year: String, status: Status): LiveData<List<SpendingSummary>>{
        return spendingDao.getSpendingsByMonthAndYear(month, year, status)
    }

    fun getSpendingByYear(year: String, status: Status): LiveData<List<YearSpendingSummary>>{
        return spendingDao.getYearSpendingsByYear(year, status)
    }
}