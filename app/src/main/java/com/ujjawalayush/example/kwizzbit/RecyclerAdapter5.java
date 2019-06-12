package com.ujjawalayush.example.kwizzbit;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter5 extends RecyclerView.Adapter<RecyclerAdapter5.RecyclerItemViewHolder3> {

    private ArrayList<RecyclerData4> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    int i,j;
    Context context;
    String m, n, friend;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseUser user;
    public RecyclerAdapter5(ArrayList<RecyclerData4> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowe1, parent, false);
        RecyclerItemViewHolder3 holder = new RecyclerItemViewHolder3(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder3 holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.etTitleTextView.setText("" + myList.get(position).getUsername());
        Picasso.get().load(Uri.parse(myList.get(position).getPic())).into(holder.circularImageView);
        mLastPosition = position;
    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }
    public void notifyData1(ArrayList<RecyclerData4> myList){
        this.myList = myList;
        notifyItemRangeRemoved(0,myList.size());
    }
    public void notifyData(ArrayList<RecyclerData4> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public void clear() {
        int size = myList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                myList.remove(0);
            }

        }
    }
    public class RecyclerItemViewHolder3 extends RecyclerView.ViewHolder{
        private final TextView etTitleTextView;
        LinearLayout mainLayout;
        public String title, description, date;
        Button accept,reject;
        CircularImageView circularImageView;

        public RecyclerItemViewHolder3(View parent) {
            super(parent);
            etTitleTextView = (TextView) parent.findViewById(R.id.txtTitle9);
            mainLayout=(LinearLayout)parent.findViewById(R.id.mainLayout9);
            circularImageView=(CircularImageView)parent.findViewById(R.id.circular1);

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    Intent data=new Intent(v.getContext(),Other.class);
                    Bundle extras=new Bundle();
                    extras.putString("name",myList.get(i).getName());
                    extras.putString("username",myList.get(i).getUsername());
                    data.putExtra("extras",extras);
                    v.getContext().startActivity(data);
                }
            });
        }
    }


    public void UpdateList(ArrayList<RecyclerData4> newList) {
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}




