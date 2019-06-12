package com.ujjawalayush.example.kwizzbit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Received extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerAdapter4 mAdapter;
    ArrayList<RecyclerData4> myList=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.received);
        toolbar=(Toolbar)findViewById(R.id.toolbar6);
        toolbar.setTitle("Friend Requests");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView6);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter4(myList);
        recyclerView.setAdapter(mAdapter);
        recycler();
    }

    private void recycler() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                        final String k = dataSnapshot1.getKey();
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference1.child(k).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("Profile Pic")) {
                                    RecyclerData4 mLog = new RecyclerData4();
                                    mLog.setName(k);
                                    mLog.setUsername(dataSnapshot.child("Username").getValue().toString());
                                    mLog.setPic(dataSnapshot.child("Profile Pic").getValue().toString());
                                    myList.add(mLog);
                                    mAdapter.notifyData(myList);
                                }
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
}
