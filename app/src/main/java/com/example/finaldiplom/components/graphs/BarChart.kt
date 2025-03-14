package com.example.finaldiplom.components.graphs

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finaldiplom.components.DataSource
import com.example.finaldiplom.data_storage.YearSpendingSummary
import com.example.finaldiplom.database.SpendingViewModel
import com.example.finaldiplom.enums.Status
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun BarChartView() {
    val datasource = DataSource()
    val status = Status.OUTCOME
    val spendingViewModel: SpendingViewModel = viewModel()
    val spendingsLiveData: LiveData<List<YearSpendingSummary>> = spendingViewModel.getSpendingByYear(datasource.year, status)
    val spendings by spendingsLiveData.observeAsState(initial = emptyList())

    AndroidView(
        modifier = Modifier.size(300.dp).fillMaxSize().fillMaxWidth(),
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(true)
                xAxis.textSize = 8f
                xAxis.labelRotationAngle = 45f
                xAxis.granularity = 1f
                xAxis.labelCount = 12
                axisRight.isEnabled = false
                axisLeft.granularity = 1f
                axisLeft.setDrawGridLines(true)
            }

        },
        update = { chart ->
            val allMonths = listOf(
                "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
            )

            val monthValues = allMonths.associateWith { 0f }.toMutableMap()

            spendings.forEach { spending ->
                val amount = spending.money.replace(",", ".").toFloatOrNull() ?: 0f
                val month = spending.month.uppercase()
                monthValues[month] = monthValues.getOrDefault(month, 0f) + amount
            }

            val entries = allMonths.mapIndexed { index, month ->
                BarEntry(index.toFloat(), monthValues[month] ?: 0f)
            }

            val dataSet = BarDataSet(entries, "EXPENSES").apply {
                color = Color.BLUE
                valueTextColor = Color.BLACK
            }

            val data = BarData(dataSet)
            chart.data = data


            chart.xAxis.valueFormatter = IndexAxisValueFormatter(allMonths)
            chart.xAxis.labelRotationAngle = -45f
            chart.invalidate()
        }
    )
}


