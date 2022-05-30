package com.manager.repot_two.chart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.user.R;

import java.util.ArrayList;

public class RadarCharActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_char);
        RadarChart radarChart =findViewById(R.id.radarChart);

        ArrayList<RadarEntry> visitorsForFirstWebsite = new ArrayList<>();
        visitorsForFirstWebsite.add(new RadarEntry(420));
        visitorsForFirstWebsite.add(new RadarEntry(500));
        visitorsForFirstWebsite.add(new RadarEntry(900));
        visitorsForFirstWebsite.add(new RadarEntry(400));
        visitorsForFirstWebsite.add(new RadarEntry(700));
        visitorsForFirstWebsite.add(new RadarEntry(200));
        visitorsForFirstWebsite.add(new RadarEntry(450));
        visitorsForFirstWebsite.add(new RadarEntry(680));
        visitorsForFirstWebsite.add(new RadarEntry(740));

        RadarDataSet radarDataSetForFirstWebsite = new RadarDataSet(visitorsForFirstWebsite,"Website 1");
        radarDataSetForFirstWebsite.setColor(Color.RED);
        radarDataSetForFirstWebsite.setLineWidth(2f);
        radarDataSetForFirstWebsite.setValueTextColor(Color.RED);
        radarDataSetForFirstWebsite.setValueTextSize(14f);

        ArrayList<RadarEntry> visitorsForSecondWebsite = new ArrayList<>();
        visitorsForSecondWebsite.add(new RadarEntry(310));
        visitorsForSecondWebsite.add(new RadarEntry(445));
        visitorsForSecondWebsite.add(new RadarEntry(657));
        visitorsForSecondWebsite.add(new RadarEntry(850));
        visitorsForSecondWebsite.add(new RadarEntry(750));
        visitorsForSecondWebsite.add(new RadarEntry(200));
        visitorsForSecondWebsite.add(new RadarEntry(448));
        visitorsForSecondWebsite.add(new RadarEntry(354));
        visitorsForSecondWebsite.add(new RadarEntry(700));

        RadarDataSet radarDataSetForSecondWebsite = new RadarDataSet(visitorsForSecondWebsite,"Website 2");
        radarDataSetForSecondWebsite.setColor(Color.BLUE);
        radarDataSetForSecondWebsite.setLineWidth(2f);
        radarDataSetForSecondWebsite.setValueTextColor(Color.BLUE);
        radarDataSetForSecondWebsite.setValueTextSize(14f);

        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSetForFirstWebsite);

        radarData.addDataSet(radarDataSetForSecondWebsite);

        String[] labels = {"2014","2015","2016","2017","2018","2019","2020","2021","2022"};
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.getDescription().setText("Radar Chart Example");
        radarChart.setData(radarData);


    }
}
