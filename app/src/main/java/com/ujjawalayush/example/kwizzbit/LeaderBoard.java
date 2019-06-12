package com.ujjawalayush.example.kwizzbit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerAdapter6 mAdapter;
    ArrayList<RecyclerData> myList,newList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        toolbar=(Toolbar)findViewById(R.id.toolbar8);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView8);
        toolbar.setTitle("LeaderBoard");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LinearLayoutManager linear=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linear);
        mAdapter=new RecyclerAdapter6(myList);
        myList=new ArrayList<>();
        newList=new ArrayList<>();
        recyclerView.setAdapter(mAdapter);
        recycler();
    }

    private void recycler() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Leaderboard");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    RecyclerData mLog=new RecyclerData();
                    mLog.ScoreCard(Float.parseFloat(dataSnapshot1.getValue().toString()),dataSnapshot1.getKey());
                    newList.add(mLog);
                }
                Sorter sorter=new Sorter(newList);
                myList=sorter.getSortedByScore();
                mAdapter.notifyData(myList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
