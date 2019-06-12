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

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.RecyclerItemViewHolder3> {
    private ArrayList<RecyclerData3> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    int i;
    Context context;
    String m, n, friend;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseUser user;
    public RecyclerAdapter3(ArrayList<RecyclerData3> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);
        RecyclerItemViewHolder3 holder = new RecyclerItemViewHolder3(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder3 holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.etTitleTextView.setText("" + myList.get(position).getGroup());
        mLastPosition = position;
        Picasso.get().load(Uri.parse(myList.get(position).getPic())).into(holder.circularImageView);

        m = myList.get(position).getGroup();
        friend = myList.get(position).getFriend();
    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }
    public void notifyData1(ArrayList<RecyclerData3> myList){
        this.myList = myList;
        notifyItemRangeRemoved(0,myList.size());
    }
    public void notifyData(ArrayList<RecyclerData3> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder3 extends RecyclerView.ViewHolder{
        private final TextView etTitleTextView;
        LinearLayout mainLayout;
        public String title, description, date;
        CircularImageView circularImageView;
        public RecyclerItemViewHolder3(View parent) {
            super(parent);
            etTitleTextView = (TextView) parent.findViewById(R.id.txtTitle1);
            mainLayout = (LinearLayout) parent.findViewById(R.id.mainLayout1);
            circularImageView=(CircularImageView)parent.findViewById(R.id.circular);
            title = etTitleTextView.getText().toString();
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context=v.getContext();
                    i=getAdapterPosition();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Requests").child(myList.get(i).getFriend());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog);
                                    builder.setTitle("Do you wish to withdraw your friend request?");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Requests").child(myList.get(i).getFriend());
                                            databaseReference2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                            Toast.makeText(context, "Friend Request Successfully withdrawn", Toast.LENGTH_LONG).show();

                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();
                            }else
                            {
                                DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference().child("Friends").child(myList.get(i).getFriend());
                                databaseReference3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (!dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog);
                                            builder.setTitle("Do you wish to send friend request?");
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    r.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Requests").child(myList.get(i).getFriend());
                                                            databaseReference2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Username").setValue(dataSnapshot.child("Username").getValue().toString());
                                                            databaseReference2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile Pic").setValue(dataSnapshot.child("Profile Pic").getValue().toString());
                                                            Toast.makeText(context, "Friend Request Successfully sent", Toast.LENGTH_LONG).show();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });

                                                }
                                            });
                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder.show();
                                        }
                                        else{
                                            Toast.makeText(context, "You guys are friends already", Toast.LENGTH_LONG).show();

                                        }
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
            });
        }
    }

    public void UpdateList(ArrayList<RecyclerData3> newList) {
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}



