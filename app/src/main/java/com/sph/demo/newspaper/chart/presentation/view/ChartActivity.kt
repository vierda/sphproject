package com.sph.demo.newspaper.chart.presentation.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.sph.demo.newspaper.R
import com.sph.demo.newspaper.common.data.model.Record
import com.sph.demo.newspaper.common.util.Util
import kotlinx.android.synthetic.main.activity_chart.*


class ChartActivity : AppCompatActivity() {

    companion object {
        val RECORD_DATA = "recordData"
        val YEAR = "year"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val bundle = intent.extras
        if (bundle != null) {
            val data = bundle.getString(RECORD_DATA)
            val year = bundle.getString(YEAR)

            if (data != null){
                val records = Util.initGson().fromJson(data, Array<Record>::class.java)
                initLineChart(year, records)
            }

        }


        close_button.setOnClickListener(View.OnClickListener { view ->
            this.finish()
        })
    }

    private fun initLineChart(year: String, records: Array<Record>) {
        val entries = ArrayList<Entry>()
        val quarters = ArrayList<String>()

        records.forEachIndexed { index, record ->

            val value = String.format("%.3f", record.volumeOfMobileData).toFloat()
            entries.add(Entry(index.toFloat(), value))
            quarters.add(record.quarter.split("-")[1])

        }

        val dataSet = LineDataSet(entries, "Mobile Data Usage $year")
        dataSet.setColors(getColor(R.color.color_43b02a))
        dataSet.valueTextColor = getColor(R.color.colorPrimaryDark)


        val formatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return quarters.get(value.toInt())
            }
        }


        line_chart.xAxis.isEnabled = true
        line_chart.axisRight.isEnabled = false

        line_chart.xAxis.granularity = 1f
        line_chart.xAxis.setValueFormatter(formatter)
        line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        line_chart.xAxis.setDrawGridLines(false)
        line_chart.axisLeft.setDrawGridLines(false)
        line_chart.axisRight.setDrawGridLines(false)

        line_chart.description = null

        val lineData = LineData(dataSet)
        lineData.setValueTextSize(10f)

        line_chart.data = lineData
        line_chart.invalidate()

    }
}