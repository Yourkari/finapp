package com.example.finapp.diagrams;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.finapp.R;
import com.example.finapp.enums.ApplicationEnums;
import com.example.finapp.model.Data;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DiagramFragment extends Fragment {

    private PieChart pieChart;
    private Spinner spinner;

    private float totalIncomeResult;
    private float totalExpenseResult;

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_graphics, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        spinner = view.findViewById(R.id.spinner);

        List<String> categories = new ArrayList<>();
        categories.add(ApplicationEnums.INCOME.getCode());
        categories.add(ApplicationEnums.EXCHANGE.getCode());
        categories.add(ApplicationEnums.UNION_IE.getCode());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(dataAdapter);
        loadTotalValues();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ApplicationEnums.INCOME.getCode().equals(String.valueOf(spinner.getSelectedItem()))) {
                    createIncomeDiagram();
                } else if (ApplicationEnums.EXCHANGE.getCode().equals(String.valueOf(spinner.getSelectedItem()))) {
                    createExchangeDiagram();
                } else {
                    createIncomeExchangeDiagram();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void createIncomeExchangeDiagram() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(totalIncomeResult, ApplicationEnums.INCOME.getCode() + " (" + totalIncomeResult + ")"));
        pieEntries.add(new PieEntry(totalExpenseResult, ApplicationEnums.EXCHANGE.getCode() + " (" + totalExpenseResult + ")"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, ApplicationEnums.UNION_IE.getCode());
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((pieDataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
        pieChart.animate();
    }

    private void createIncomeDiagram() {
        pieChart.setData(null);
        //pieChart = null;
    }

    private void createExchangeDiagram() {
        pieChart.setData(null);
        //pieChart = null;
    }

    private void loadTotalValues() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uid = mUser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpanseData").child(uid);

        mIncomeDatabase.keepSynced(true);
        mExpenseDatabase.keepSynced(true);
        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalSum = 0;

                for (DataSnapshot mysnap : snapshot.getChildren()) {
                    Data data = mysnap.getValue(Data.class);

                    assert data != null;
                    totalSum += data.getAmount();

                    totalIncomeResult = (float) totalSum;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalsum = 0;
                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                    Data data = mysnapshot.getValue(Data.class);
                    assert data != null;
                    totalsum += data.getAmount();
                    totalExpenseResult = (float) totalsum;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
