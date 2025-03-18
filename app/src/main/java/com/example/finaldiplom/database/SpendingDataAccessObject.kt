package com.example.finaldiplom.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.finaldiplom.data_storage.SpendingSummary
import com.example.finaldiplom.data_storage.YearSpendingSummary
import com.example.finaldiplom.database.entities.Budget
import com.example.finaldiplom.database.entities.Spending
import com.example.finaldiplom.enums.Status

@Dao
interface SpendingDataAccessObject {

    @Query("SELECT * FROM SPENDING")
    fun getAllSpendings(): LiveData<List<Spending>>

    @Query("SELECT ID,DATE,MONTH, YEAR, STATUS,MONEY,CATEGORY,NOTE FROM SPENDING WHERE DATE = :date")
    fun getPreciseSpending(date: String): LiveData<List<Spending>>

    @Query("SELECT money, category FROM SPENDING WHERE MONTH = :month AND YEAR = :year AND STATUS = :status")
    fun getSpendingsByMonthAndYear(month: String, year: String, status: Status): LiveData<List<SpendingSummary>>

    @Query("SELECT MONTH, MONEY FROM SPENDING WHERE YEAR = :year AND STATUS = :status")
    fun getYearSpendingsByYear(year: String, status: Status): LiveData<List<YearSpendingSummary>>

    @Insert
    fun addSpending(spending: Spending)

    @Query("SELECT * FROM BUDGET WHERE ID = :id LIMIT 1")
    fun getBudgetById(id: Int): Budget?

    @Insert
    fun addBudget(budget: Budget)

    @Query("SELECT amount FROM BUDGET WHERE ID = :id")
    fun getBudget(id: Int): LiveData<Float>

    @Query("UPDATE BUDGET SET AMOUNT = :amount WHERE ID = :id")
    fun updateBudget(amount: Float, id: Int)

    @Query("DELETE FROM SPENDING WHERE ID = :id")
    fun deleteSpending(id: Int)
}