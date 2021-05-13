package com.example.finapp.diagrams;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class DiagramFragment extends AppCompatActivity {

    private Button button;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        chart = findViewById(R.id.chart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Массив координат точек
                ArrayList<Entry> entriesFirst = new ArrayList<>();
                entriesFirst.add(new Entry(1f, 5f));
                entriesFirst.add(new Entry(2f, 2f));
                entriesFirst.add(new Entry(3f, 1f));
                entriesFirst.add(new Entry(4f, -3f));
                entriesFirst.add(new Entry(5f, 4f));
                entriesFirst.add(new Entry(6f, 1f));

                // На основании массива точек создадим первую линию с названием
                LineDataSet datasetFirst = new LineDataSet(entriesFirst, "График первый");
                // График будет заполненным
                datasetFirst.setDrawFilled(true);

                // Массив координат точек второй линии
                ArrayList<Entry> entriesSecond = new ArrayList<>();
                entriesSecond.add(new Entry(0.5f, 0f));
                entriesSecond.add(new Entry(2.5f, 2f));
                entriesSecond.add(new Entry(3.5f, 1f));
                entriesSecond.add(new Entry(3.6f, 2f));
                entriesSecond.add(new Entry(4f, 0.5f));
                entriesSecond.add(new Entry(5.1f, -0.5f));

                // На основании массива точек создаем вторую линию с названием
                LineDataSet datasetSecond = new LineDataSet(entriesSecond, "График второй");
                // График будет зеленого цвета
                datasetSecond.setColor(Color.GREEN);
                // График будет плавным
                datasetSecond.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                // Линии графиков соберем в один массив
                ArrayList<ILineDataSet> dataSets = new ArrayList();
                dataSets.add(datasetFirst);
                dataSets.add(datasetSecond);

                // Создадим переменную  данных для графика
                LineData data = new LineData(dataSets);
                // Передадим данные для графика в сам график
                chart.setData(data);

                // График будет анимироваться 0.5 секунды
                chart.animateY(500);


                // Не забудем отправить команду на перерисовку кадра, иначе график не отобразится
                // Но если график анимируется, то он отрисуется самостоятельно, так что
                // команда ниже не обязательна
                //chart.invalidate();
            }
        });
    }


}
