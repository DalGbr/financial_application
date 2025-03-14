package com.example.finaldiplom.components.graphs

import android.graphics.Color
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finaldiplom.data_storage.SpendingSummary
import com.example.finaldiplom.database.SpendingViewModel
import com.example.finaldiplom.enums.Status
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.DecimalFormat
import java.time.LocalDate

@Composable
fun PieChartView() {
    val currentDate = LocalDate.now()
    val year = currentDate.year.toString()
    val month = currentDate.month.toString()
    val status = Status.OUTCOME

    val spendingViewModel: SpendingViewModel = viewModel()
    val spendings: LiveData<List<SpendingSummary>> = spendingViewModel.getSpendingByData(month, year, status)

    val spendingList by spendings.observeAsState(emptyList())

    val categorySums = mutableMapOf<String, Float>()
    spendingList.forEach { spending ->
        val categoryName = spending.category?.name
        val moneyFloat = spending.money.toFloatOrNull()

        if (categoryName != null && moneyFloat != null) {
            categorySums[categoryName] = categorySums.getOrDefault(categoryName, 0f) + moneyFloat
        }
    }

    val entries = categorySums.map { (category, sum) ->
        PieEntry(sum, category)
    }

    AndroidView(
        modifier = Modifier.size(300.dp),
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                setUsePercentValues(false)
                setEntryLabelColor(Color.BLACK)
                legend.isEnabled = false
                setEntryLabelTextSize(8f)
            }
        },
        update = { pieChart ->
            val dataSet = PieDataSet(entries, "").apply {
                colors = ColorTemplate.COLORFUL_COLORS.toList()
                valueTextSize = 8f
                valueTextColor = Color.BLACK
                sliceSpace = 2f
            }

            val decimalFormat = DecimalFormat("$#.##")
            dataSet.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return decimalFormat.format(value)
                }
            }

            pieChart.data = PieData(dataSet)
            pieChart.setDrawEntryLabels(true)
            pieChart.invalidate()
        }
    )
}