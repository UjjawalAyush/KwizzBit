package com.ujjawalayush.example.kwizzbit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class AddFriends extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    android.support.v7.widget.SearchView searchView;
    DatabaseReference databaseReference,reference;
    private RecyclerAdapter3 mAdapter,gAdapter;
    String m,n;
    ArrayList<RecyclerData3> newList;
    ArrayList<RecyclerData3> myList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriends);
        toolbar=(Toolbar)findViewById(R.id.toolbar5);
        toolbar.setTitle("Find friends");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView5);
        Resources res = getResources();
        setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter=new RecyclerAdapter3(myList);
        myList.clear();
        recyclerView.setAdapter(mAdapter);
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(linearLayoutManager);
        Intent data=getIntent();
        a();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void a() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String k = snapshot.getKey();
                    if (k.equals(user.getUid()) == false) {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users");
                        reference.child(k).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("Profile Pic")) {
                                    RecyclerData3 mLog;
                                    gAdapter = new RecyclerAdapter3(newList);
                                    mLog = new RecyclerData3();
                                    mLog.setGroup(dataSnapshot.child("Username").getValue().toString());
                                    mLog.setPic(dataSnapshot.child("Profile Pic").getValue().toString());
                                    mLog.setFriend(reference.child(k).getKey());
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
        inflater.inflate(R.menu.addfriendstoolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.search11);
        searchView = (android.support.v7.widget.SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String t = s.toLowerCase();
        ArrayList<RecyclerData3> newList = new ArrayList<>();
        for(RecyclerData3 n : myList)
        {
            if(n.getGroup().toLowerCase().contains(t)) {
                newList.add(n);
            }
        }
        mAdapter.UpdateList(newList);
        return true;
    }

}
