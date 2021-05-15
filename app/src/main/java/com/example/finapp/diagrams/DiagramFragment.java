package com.example.finapp.diagrams;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.finapp.R;
import com.example.finapp.enums.ApplicationEnums;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiagramFragment extends Fragment {

    private LineChart chart;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_graphics, container, false);

        chart = view.findViewById(R.id.chart);
        spinner = view.findViewById(R.id.spinner);

        List<String> categories = new ArrayList<>();
        categories.add(ApplicationEnums.INCOME.getCode());
        categories.add(ApplicationEnums.EXCHANGE.getCode());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (ApplicationEnums.INCOME.getCode().equals(String.valueOf(spinner.getSelectedItem()))) {
                    createIncomeDiagram();
                } else {
                    createExchangeDiagram();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void createIncomeDiagram() {

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
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
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

    private void createExchangeDiagram() {
        // Массив координат точек второй линии
        ArrayList<Entry> entriesSecond = new ArrayList<>();
        entriesSecond.add(new Entry(0.5f, 0f));
        entriesSecond.add(new Entry(2.5f, 2f));
        entriesSecond.add(new Entry(5.5f, 1f));
        entriesSecond.add(new Entry(2.1f, 2f));

        // На основании массива точек создаем вторую линию с названием
        LineDataSet datasetSecond = new LineDataSet(entriesSecond, "График второй");
        // График будет зеленого цвета
        datasetSecond.setColor(Color.GREEN);
        // График будет плавным
        datasetSecond.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Линии графиков соберем в один массив
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
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
}
