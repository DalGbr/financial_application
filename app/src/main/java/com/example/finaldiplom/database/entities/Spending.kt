package com.example.finaldiplom.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.finaldiplom.enums.Category
import com.example.finaldiplom.enums.Status

@Entity
data class Spending(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var date: String,
    var month: String,
    var year: String,
    var status: Status?,
    var money: String,
    var category: Category?,
    var note: String
)

@Entity
data class Budget(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var amount: Float,
)