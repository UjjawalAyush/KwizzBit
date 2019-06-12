package com.ujjawalayush.example.kwizzbit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText editText,editText1;
    private Button button,b2;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth =FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(isNe()==false){
            Toast.makeText(MainActivity.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if(user!=null){
            if(user.getEmail()==null) {
            }
            else{

            }
            Intent data=new Intent(MainActivity.this,MainPage.class);
            startActivity(data);
        }
        else{

            try {
                json();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.editText);
        editText1=(EditText)findViewById(R.id.editText2);
        progressDialog = new ProgressDialog(this);
    }
    public boolean isNe(){
        try {
            NetworkInfo networkInfo = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }
        catch(NullPointerException e){
            return false;
        }
    }
    public void onClick(View v){
        String email,password;
        email=editText.getText().toString().trim();
        password=editText1.getText().toString().trim();
        if(email.equals(""))
        {
            editText.setError("E-Mail entry is mandatory");
            editText.requestFocus();
            return;
        }
        if(password.equals(""))
        {
            editText1.setError("Password entry is mandatory");
            editText1.requestFocus();
            return;
        }
        progressDialog.setMessage("Logging in!Please wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(isNe()==false)
                {
                    Toast.makeText(MainActivity.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Successfully Logged In",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent data = new Intent(MainActivity.this, MainPage.class);
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    String user_id=user.getUid();
                    data.putExtra("user_id",user_id);
                    startActivity(data);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid E-Mail Id or Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    public void onClick1(View v) {
        Intent data = new Intent(MainActivity.this, SignUp.class);
        startActivity(data);
    }
    public void onClick2(View v){
        Intent data=new Intent(MainActivity.this,Phone.class);
        startActivity(data);
    }
    public void json() throws IOException, JSONException {
        DBAdapter db=new DBAdapter(this);
        db.open();

        int j=0;
        int k=0;
        for(k=0;k<15;k++)
        {
            if(k==0){
                j=R.raw.music;
            }
            else if(k==1){
                j=R.raw.general;
            }
            else if(k==2){
                j=R.raw.books;
            }
            else if(k==3){
                j=R.raw.television;
            }
            else if(k==4){
                j=R.raw.film;
            }
            else if(k==5){
                j=R.raw.video;
            }
            else if(k==6){
                j=R.raw.science;
            }
            else if(k==7){
                j=R.raw.computer;
            }
            else if(k==8){
                j=R.raw.sports;
            }
            else if(k==9){
                j=R.raw.geography;
            }
            else if(k==10){
                j=R.raw.history;
            }
            else if(k==11){
                j=R.raw.vehicles;
            }
            else if(k==12){
                j=R.raw.anime;
            }
            else if(k==13){
                j=R.raw.cartoon;
            }
            else if(k==14){
                j=R.raw.miscellaneous;
            }
            InputStream is = getResources().openRawResource(j);

            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                is.close();
            }

            String jsonString = writer.toString();
            jsonString=jsonString.replaceAll("&#039;","'");
            jsonString=jsonString.replaceAll("&quot;","");
            jsonString=jsonString.replaceAll("&It;","<");
            jsonString=jsonString.replaceAll("&gt;",">");



            JSONObject jsonObject=new JSONObject(jsonString);
            JSONArray jsonArray=(JSONArray)jsonObject.get("results");
            int i=0;

            long id=0;
            /*
            for(i=0;i<1000;i++)
            {
                db.deleteContact(i);
            }*/
            for(i=0;i<50;i++)
            {
                JSONObject object= (JSONObject)jsonArray.get(i);
                String question=object.get("question").toString();
                String category=object.get("category").toString();
                String type=object.get("type").toString();
                String difficulty=object.get("difficulty").toString();
                String correct=object.get("correct_answer").toString();
                JSONArray object2=(JSONArray) object.get("incorrect_answers");
                String in1=object2.get(0).toString();
                String in2=object2.get(1).toString();
                String in3=object2.get(2).toString();
                id=db.insertContact(category,type,difficulty,question,correct,in1,in2,in3);
            }
        }

    }
    public String replaceAll(String regex, String replacement) {
        return Pattern.compile(regex).matcher(regex).replaceAll(replacement);
    }
}
