package com.manager.repot_two.chart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.user.R;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> visitors = new ArrayList<>();

        visitors.add(new BarEntry(2014,420));
        visitors.add(new BarEntry(2015,447));
        visitors.add(new BarEntry(2016,500));
        visitors.add(new BarEntry(2017,300));
        visitors.add(new BarEntry(2018,560));
        visitors.add(new BarEntry(2019,660));
        visitors.add(new BarEntry(2020,550));
        visitors.add(new BarEntry(2021,633));
        visitors.add(new BarEntry(2022,480));

        BarDataSet barDataSet = new BarDataSet(visitors,"Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);

    }
}
