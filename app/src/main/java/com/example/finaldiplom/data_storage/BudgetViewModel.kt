package com.example.finaldiplom.data_storage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BudgetViewModel: ViewModel() {
    var amount by mutableStateOf("0")
        private set

    fun updateAmount(newAmount: String) {
        amount = newAmount
    }
}