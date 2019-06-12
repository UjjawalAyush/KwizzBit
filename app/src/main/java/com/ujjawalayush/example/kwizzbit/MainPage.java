package com.ujjawalayush.example.kwizzbit;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import  android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    DrawerLayout drawerLayout;
    private RecyclerAdapter mAdapter, gAdapter;
    ArrayList<String> myList = new ArrayList<>();
    String f;
    private RecyclerView recyclerView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    Button button, b1;
    Drawable drawable1;
    FirebaseAuth firebaseAuth;
    String user_id;
    String b = "";
    NavigationView navigationView;
    DatabaseReference databaseReference, reference;
    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        DBAdapter db=new DBAdapter(this);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Choose Category");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Resources res = getResources();
        drawable1 = ResourcesCompat.getDrawable(res, R.drawable.b, null);
        mAdapter = new RecyclerAdapter(myList);
        recyclerView.setAdapter(mAdapter);
        try {
            recycler();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void recycler() throws IOException, JSONException {
        myList.add("Anime&Manga");
        myList.add("Books");
        myList.add("Cartoons");
        myList.add("Computers");
        myList.add("Films");
        myList.add("General Knowledge");
        myList.add("Geography");
        myList.add("History");
        myList.add("Miscellaneous");
        myList.add("Music");
        myList.add("Science&Nature");
        myList.add("Sports");
        myList.add("Television");
        myList.add("Vehicles");
        myList.add("Video games");
        mAdapter.notifyData(myList);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent data=new Intent(getApplicationContext(),MainActivity.class);
                data.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(data);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        MenuItem menuItem1 = menu.findItem(R.id.view);
        menuItem1.setIcon(drawable1);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()){
            case R.id.view:
                Intent data1=new Intent(MainPage.this,View1.class);
                startActivity(data1);
                break;
            case R.id.friends:
                Intent data=new Intent(MainPage.this,Friends.class);
                startActivity(data);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String t = s.toLowerCase();
        ArrayList<String> newList = new ArrayList<>();
        for (String n : myList) {
            if (n.toLowerCase().contains(t)) {
                newList.add(n);
            }
        }
        mAdapter.UpdateList(newList);
        return true;
    }
}
