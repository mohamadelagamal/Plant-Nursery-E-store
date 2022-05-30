package com.manager.repot_two

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.manager.repot_two.chart.BarChartActivity
import com.manager.repot_two.chart.PieChartActivity
import com.manager.repot_two.chart.RadarCharActivity
import com.user.R

class ReportTwoActivity : AppCompatActivity() {
    lateinit var barChart :Button
    lateinit var pieChart:Button
    lateinit var radarChart:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_two)
        barChart = findViewById(R.id.buttonBarChart)
        pieChart = findViewById(R.id.buttonPieChart)
        radarChart = findViewById(R.id.buttonRadarChart)

        barChart.setOnClickListener {
            barChart()
        }
        pieChart.setOnClickListener {
            pieChart()
        }
        radarChart.setOnClickListener {
            radarChart()
        }
    }
    fun barChart(){
        val intent = Intent(this, BarChartActivity::class.java)
        startActivity(intent)
    }
    fun pieChart(){
        val intent = Intent(this, PieChartActivity::class.java)
        startActivity(intent)
    }
    fun radarChart(){
        val intent = Intent(this, RadarCharActivity::class.java)
        startActivity(intent)
    }
}