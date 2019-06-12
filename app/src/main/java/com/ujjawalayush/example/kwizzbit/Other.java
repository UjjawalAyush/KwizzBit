package com.ujjawalayush.example.kwizzbit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Other extends AppCompatActivity {
    BarChart barChart;
    TextView questions;
    int i=0,j=0;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ArrayList<BarEntry> entries=new ArrayList<>();
    ArrayList<String> category1=new ArrayList<>();
    Toolbar toolbar;
    TextView time1;
    ProgressBar progressBar;
    YAxis yAxis;
    String name,username;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view1);
        toolbar=(Toolbar)findViewById(R.id.toolbar3);
        toolbar.setTitle("Stats");
        questions=(TextView)findViewById(R.id.textView12);
        questions.setVisibility(View.INVISIBLE);
        time1=(TextView)findViewById(R.id.textView13);
        time1.setVisibility(View.INVISIBLE);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        barChart=(BarChart)findViewById(R.id.barChart);
        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setAxisMaximum(20);
        YAxis yAxis1=barChart.getAxisRight();
        yAxis1.setAxisMaximum(20);
        barChart.setNoDataText("Click to view the chart");
        barChart.setHorizontalScrollBarEnabled(true);
        Intent data=getIntent();
        name=data.getBundleExtra("extras").getString("name");
        username=data.getBundleExtra("extras").getString("username");
        barChart.getDescription().setText("Scroll to view more");
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Quiz").child("Scores").child(name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String x=Long.toString(dataSnapshot.getChildrenCount());
                questions.setText("Total Quizzes Played: "+x);
                questions.setVisibility(View.VISIBLE);
                if(dataSnapshot.hasChildren()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        j++;
                        final String time = dataSnapshot1.getKey();
                        long current = Long.parseLong(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss");
                        Date resultdate = new Date(current);
                        final String date = sdf.format(resultdate);
                        if(j==Integer.parseInt(x)){
                            time1.setText("Last Played: "+ date);
                            time1.setVisibility(View.VISIBLE);
                        }

                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Quiz").child("Scores").child(name).child(time);
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String username=dataSnapshot.child("Username").getValue().toString();
                                String score=dataSnapshot.child("Score").getValue().toString();
                                String category=dataSnapshot.child("Category").getValue().toString();
                                BarEntry a=new BarEntry((float)i,Integer.parseInt(score));
                                entries.add(a);
                                category1.add(category);
                                BarDataSet barDataSet=new BarDataSet(entries,"Category");
                                barDataSet.setColors(new int[]{ContextCompat.getColor(barChart.getContext(),R.color.colorAccent),ContextCompat.getColor(barChart.getContext(),R.color.colorAccent1),ContextCompat.getColor(barChart.getContext(),R.color.colorAccent2),ContextCompat.getColor(barChart.getContext(),R.color.colorAccent4),ContextCompat.getColor(barChart.getContext(),R.color.colorAccent3),ContextCompat.getColor(barChart.getContext(),R.color.colorAccent6),ContextCompat.getColor(barChart.getContext(),R.color.colorAccent5),ContextCompat.getColor(barChart.getContext(),R.color.colorAccent7)});
                                BarData barData=new BarData(barDataSet);
                                barChart.getXAxis().setLabelRotationAngle(90);
                                barChart.getXAxis().setGranularityEnabled(true);
                                barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                                    @Override
                                    public String getFormattedValue(float value, AxisBase axis) {
                                        return category1.get((int)value);
                                    }
                                });
                                barData.setBarWidth(0.5f);
                                barChart.setData(barData);
                                barChart.setVisibleXRangeMaximum(5);
                                barChart.setTouchEnabled(true);
                                barChart.setDragEnabled(true);
                                barChart.setScaleYEnabled(true);
                                barChart.setScaleXEnabled(false);
                                i++;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public int getColor1(Context context, int score){
        int colour1;
        if(score<3){
            colour1=R.color.colorAccent1;
        }
        else if(score<6){
            colour1=R.color.colorAccent6;
        }
        else if(score<8){
            colour1=R.color.colorAccent7;

        }
        else if(score<10){
            colour1=R.color.colorAccent3;

        }
        else if(score<12){
            colour1=R.color.colorAccent4;

        }
        else if(score<15){
            colour1=R.color.colorAccent2;

        }
        else if(score<18){
            colour1=R.color.colorAccent5;

        }
        else{
            colour1=R.color.colorAccent;
        }

        return colour1;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
