package com.ujjawalayush.example.kwizzbit;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {
    String category;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        Intent data=getIntent();
        category=data.getStringExtra("Category");
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(category);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onClick(View v){
        Intent data=new Intent(Quiz.this,Start.class);
        data.putExtra("Category",category);
        startActivity(data);
    }
}
