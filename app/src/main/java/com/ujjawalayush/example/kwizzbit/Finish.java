package com.ujjawalayush.example.kwizzbit;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Finish extends AppCompatActivity {
    int sign[],position1;
    String correct,position,category;
    RecyclerAdapter2 mAdapter;
    ArrayList<RecyclerData2> myList=new ArrayList<>();
    TextView textView10,textView11;
    RecyclerView recyclerView;
    float score=0;
    float getScore=0,fi=0;
    String score1,getScore1,u;
    FirebaseUser user;
    Cursor c;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish);
        Intent data=getIntent();
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle("Result");
        setSupportActionBar(toolbar);
        correct=data.getBundleExtra("extras").getString("correct");
        sign=data.getBundleExtra("extras").getIntArray("sign");
        category=data.getBundleExtra("extras").getString("category");
        position=data.getBundleExtra("extras").getString("position");
        textView10=(TextView)findViewById(R.id.textView10);
        textView11=(TextView)findViewById(R.id.textView11);
        textView10.setText("Correct Answers: "+correct);
        position1=Integer.parseInt(position);
        int i=Integer.parseInt(correct);
        if(i<5){
            textView11.setText("Nice Try but You suck!");
        }
        else if(i<10){
            textView11.setText("Good but needs improvement");
        }
        else if(i<15){
            textView11.setText("Awesome,You really are a master");
        }
        else if(i<20){
            textView11.setText("You nailed it bro");
        }
        else{
            textView11.setText("Congrats,Perfect Score!");
        }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter2(myList);
        recyclerView.setAdapter(mAdapter);
        DBAdapter db=new DBAdapter(this);
        db.open();
        c=db.getAllContacts();
        c.moveToPosition(position1);
        user=FirebaseAuth.getInstance().getCurrentUser();
        recycler();

    }
    public void recycler(){
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Username")) {
                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Quiz").child("Scores").child(user.getUid()).child(Long.toString(System.currentTimeMillis()));
                    databaseReference1.child("Username").setValue(dataSnapshot.child("Username").getValue().toString());
                    databaseReference1.child("Score").setValue(correct);
                    databaseReference1.child("Category").setValue(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        int j;
        for (j = 0; j < 20; j++) {

            RecyclerData2 mLog = new RecyclerData2();
            mLog.setQuestion(c.getString(4));
            if(sign[j]!=9){
            mLog.setYourAnswer(c.getString(sign[j]));}
            else{
                mLog.setYourAnswer("None selected");
            }
            mLog.setCorrectAnswer(c.getString(5));
            myList.add(mLog);
            mAdapter.notifyData(myList);
            c.moveToNext();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        leaderboard();
        Intent dat=new Intent(Finish.this,MainPage.class);
        startActivity(dat);
        return true;
    }
    public void leaderboard(){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Quiz").child("Scores").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    final String k=snapshot.getKey();
                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Quiz").child("Scores").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReference1.child(k).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            score1=dataSnapshot.child("Score").getValue().toString();
                            score=score+Float.parseFloat(score1);
                            getScore++;
                            fi=score/getScore;
                            DatabaseReference r=FirebaseDatabase.getInstance().getReference().child("Leaderboard");
                            r.child(dataSnapshot.child("Username").getValue().toString()).setValue(Float.toString(fi));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
