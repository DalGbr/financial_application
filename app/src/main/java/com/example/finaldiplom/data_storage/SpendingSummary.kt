package com.example.finaldiplom.data_storage

import com.example.finaldiplom.enums.Category

data class SpendingSummary(
    val money: String,
    val category: Category?
)

data class YearSpendingSummary(
    val month: String,
    val money: String
)
