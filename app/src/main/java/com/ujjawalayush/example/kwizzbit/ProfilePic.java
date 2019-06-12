package com.ujjawalayush.example.kwizzbit;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ProfilePic extends AppCompatActivity {
    int x=1;
    Uri uri;
    CircularImageView circularImageView;
    ProgressDialog progressDialog;
    StorageReference storageReference;
    String axe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepic);
        circularImageView=(CircularImageView)findViewById(R.id.circularImageView);
        progressDialog=new ProgressDialog(this);
        uri=Uri.parse("q");
    }
    public void onClick2(View v){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,x);
    }
    public void onClick(View v){

        progressDialog.setMessage("Uploading...Please Wait");
        progressDialog.show();
        if(!uri.toString().equals("q")) {
            axe = getFileExtension(uri);
            storageReference = FirebaseStorage.getInstance().getReference().child(Long.toString(System.currentTimeMillis()) + "." + getFileExtension(uri));
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference.child("Profile Pic").setValue(uri1.toString());
                            progressDialog.dismiss();
                            Toast.makeText(ProfilePic.this, "Picture successfully uploaded", Toast.LENGTH_SHORT).show();
                            Intent data = new Intent(ProfilePic.this, MainPage.class);
                            startActivity(data);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfilePic.this, "Uploading Failure", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
        else{
            Toast.makeText(ProfilePic.this,"Please choose a ProfilePic",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
    public String getFileExtension(Uri uri1){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri1));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==x&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            Picasso.get().load(uri).into(circularImageView);
        }
    }
}
