package com.ujjawalayush.example.kwizzbit;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {
    Toolbar toolbar;
    Drawable drawable,drawable1;
    RecyclerView recyclerView;
    RecyclerAdapter5 mAdapter;
    ArrayList<RecyclerData4> myList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        toolbar=(Toolbar)findViewById(R.id.toolbar4);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView4);
        toolbar.setTitle("Friends");
        toolbar.setSubtitle("Click to view friend's stats");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Resources res = getResources();
        drawable = ResourcesCompat.getDrawable(res, R.drawable.highscorepngicon11, null);
        drawable1 = ResourcesCompat.getDrawable(res, R.drawable.a, null);
        LinearLayoutManager linear=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linear);
        mAdapter=new RecyclerAdapter5(myList);
        recyclerView.setAdapter(mAdapter);
        recycler();
    }

    private void recycler() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                        final String k = dataSnapshot1.getKey();
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friendsmenu,menu);
        MenuItem menuItem=menu.findItem(R.id.leaderboard);
        menuItem.setIcon(drawable);
        MenuItem r=menu.findItem(R.id.received);
        r.setIcon(drawable1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.addfriends:
                Intent data=new Intent(Friends.this,AddFriends.class);
                startActivity(data);
                break;
            case R.id.received:
                Intent data1=new Intent(Friends.this,Received.class);
                startActivity(data1);
                break;
            case R.id.leaderboard:
                Intent data2=new Intent(Friends.this,LeaderBoard.class);
                startActivity(data2);
        }
        return true;
    }
}
