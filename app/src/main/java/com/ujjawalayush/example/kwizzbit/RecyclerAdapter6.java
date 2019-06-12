package com.ujjawalayush.example.kwizzbit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAdapter6 extends RecyclerView.Adapter<RecyclerAdapter6.RecyclerItemViewHolder2> {

    private ArrayList<RecyclerData> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    String m, n, friend;
    FirebaseAuth firebaseAuth;
    Context context;
    DatabaseReference databaseReference;
    FirebaseUser user;
    public RecyclerAdapter6(ArrayList<RecyclerData> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.w, parent, false);
        RecyclerItemViewHolder2 holder = new RecyclerItemViewHolder2(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder2 holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.etTitleTextView.setText(myList.get(position).getUsername());
        holder.mainLayout.setText(Float.toString(myList.get(position).getScore()));

    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<RecyclerData> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder2 extends RecyclerView.ViewHolder{
        private final TextView etTitleTextView;
        ImageButton b;
        TextView mainLayout,mainLayout2;
        public String title, description, date;

        public RecyclerItemViewHolder2(View parent) {
            super(parent);
            etTitleTextView = (TextView) parent.findViewById(R.id.txtTitle56);
            mainLayout = (TextView) parent.findViewById(R.id.txtTitle57);
        }
    }
}



