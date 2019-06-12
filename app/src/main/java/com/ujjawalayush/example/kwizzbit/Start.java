package com.ujjawalayush.example.kwizzbit;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Start extends AppCompatActivity {
    Toolbar toolbar;
    TextView question,difficulty,no;
    RadioGroup radio;
    String category,Question,Difficulty,correct,in1,in2,in3,in4;
    RadioButton b1,b2,b3,b4;
    int position,min,max,a1,a2,a3,a4,a=0,correct1=0;
    int sign[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    Cursor c;
    Button button;
    ArrayList<Integer> myList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        Intent data=getIntent();
        category=data.getStringExtra("Category");
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle(category);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        question=(TextView)findViewById(R.id.textView8);
        difficulty=(TextView)findViewById(R.id.textView5);
        no=(TextView)findViewById(R.id.textView9);
        radio=(RadioGroup)findViewById(R.id.radioGroup);
        b1=(RadioButton)findViewById(R.id.b1);
        b2=(RadioButton)findViewById(R.id.b2);
        b3=(RadioButton)findViewById(R.id.b3);
        b4=(RadioButton)findViewById(R.id.b4);
        if(category.equals("Music")){
            min=0;
            max=30;
        }
        else if(category.equals("General Knowledge")){
            min=50;
            max=80;
        }
        else if(category.equals("Books")){
            min=100;
            max=130;
        }
        else if(category.equals("Television")){
            min=150;
            max=180;
        }
        else if(category.equals("Films")){
            min=200;
            max=230;
        }
        else if(category.equals("Video games")){
            min=250;
            max=280;
        }
        else if(category.equals("Science&Nature")){
            min=300;
            max=330;
        }
        else if(category.equals("Computers")){
            min=350;
            max=380;
        }
        else if(category.equals("Sports")){
            min=400;
            max=430;
        }
        else if(category.equals("Geography")){
            min=450;
            max=480;
        }
        else if(category.equals("History")){
            min=500;
            max=530;
        }
        else if(category.equals("Vehicles")){
            min=550;
            max=580;
        }
        else if(category.equals("Anime&Manga")){
            min=600;
            max=630;
        }
        else if(category.equals("Cartoons")){
            min=650;
            max=680;
        }
        else if(category.equals("Miscellaneous")){
            min=700;
            max=730;
        }
        button=(Button)findViewById(R.id.button7);
        DBAdapter db=new DBAdapter(this);
        db.open();
        position=Randomizer.generate(min,max);
        c=db.getAllContacts();
        c.moveToPosition(position);
        question.setText(c.getString(4));
        difficulty.setText("Difficulty Level: "+c.getString(3));
        a1=Randomizer.generate(5,8);
        a2=getRandomWithExclusion(5,8,a1);
        a3=getRandomWithExclusion(5,8,a1,a2);
        a4=getRandomWithExclusion(5,8,a1,a2,a3);
        b1.setText(c.getString(a1));
        b2.setText(c.getString(a2));
        b3.setText(c.getString(a3));
        b4.setText(c.getString(a4));

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onClick1(View v){
        if(a==18){
            button.setText("FINISH");
        }
        if(a==19){
            int checked=radio.getCheckedRadioButtonId();
            if(checked!=-1) {
                if (a1 == 5) {
                    if (checked == b1.getId()) {
                        correct1++;
                        sign[a] = a1;
                    } else if (checked == b2.getId()) {
                        sign[a] = a2;
                    } else if (checked == b3.getId()) {
                        sign[a] = a3;
                    } else {
                        sign[a] = a4;
                    }
                } else if (a2 == 5) {
                    if (checked == b2.getId()) {
                        correct1++;
                        sign[a] = a2;

                    } else if (checked == b1.getId()) {
                        sign[a] = a1;
                    } else if (checked == b3.getId()) {
                        sign[a] = a3;
                    } else {
                        sign[a] = a4;
                    }
                } else if (a3 == 5) {
                    if (checked == b3.getId()) {
                        correct1++;
                        sign[a] = a3;

                    } else if (checked == b1.getId()) {
                        sign[a] = a1;
                    } else if (checked == b2.getId()) {
                        sign[a] = a2;
                    } else {
                        sign[a] = a4;
                    }
                } else if (a4 == 5) {
                    if (checked == b4.getId()) {
                        correct1++;
                        sign[a] = a4;

                    } else if (checked == b1.getId()) {
                        sign[a] = a1;
                    } else if (checked == b3.getId()) {
                        sign[a] = a3;
                    } else {
                        sign[a] = a2;
                    }
                }
            }
            else{
                sign[a]=9;
            }
            Intent data=new Intent(Start.this,Finish.class);
            Bundle extras=new Bundle();
            extras.putString("Category",category);
            extras.putString("correct",Integer.toString(correct1));
            extras.putIntArray("sign",sign);
            extras.putString("position",Integer.toString(position));
            extras.putString("category",category);
            data.putExtra("extras",extras);
            startActivity(data);
        }
        if(a<19){
            int checked=radio.getCheckedRadioButtonId();
            if(checked!=-1) {
                if (a1 == 5) {
                    if (checked == b1.getId()) {
                        correct1++;
                        sign[a] = a1;

                    } else if (checked == b2.getId()) {
                        sign[a] = a2;
                    } else if (checked == b3.getId()) {
                        sign[a] = a3;
                    } else {
                        sign[a] = a4;
                    }
                } else if (a2 == 5) {
                    if (checked == b2.getId()) {
                        correct1++;
                        sign[a] = a2;


                    } else if (checked == b1.getId()) {
                        sign[a] = a1;
                    } else if (checked == b3.getId()) {
                        sign[a] = a3;
                    } else {
                        sign[a] = a4;
                    }
                } else if (a3 == 5) {
                    if (checked == b3.getId()) {
                        correct1++;
                        sign[a] = a3;

                    } else if (checked == b2.getId()) {
                        sign[a] = a2;
                    } else if (checked == b1.getId()) {
                        sign[a] = a1;
                    } else {
                        sign[a] = a4;
                    }
                } else if (a4 == 5) {
                    if (checked == b4.getId()) {
                        correct1++;
                        sign[a] = a4;

                    } else if (checked == b2.getId()) {
                        sign[a] = a2;
                    } else if (checked == b3.getId()) {
                        sign[a] = a3;
                    } else {
                        sign[a] = a1;
                    }
                }
            }
            else{
                sign[a]=9;
            }
            radio.clearCheck();
        c.moveToNext();
        no.setText("Question No."+Integer.toString(a+2));
        question.setText(c.getString(4));
        difficulty.setText("Difficulty Level: "+c.getString(3));
        a1=Randomizer.generate(5,8);
        a2=getRandomWithExclusion(5,8,a1);
        a3=getRandomWithExclusion(5,8,a1,a2);
        a4=getRandomWithExclusion(5,8,a1,a2,a3);
        b1.setText(c.getString(a1));
        b2.setText(c.getString(a2));
        b3.setText(c.getString(a3));
        b4.setText(c.getString(a4));
        a++;}
    }
    public static class Randomizer
    {
        public static int generate(int min,int max)
        {
            return min + (int)(Math.random() * ((max - min) + 1));
        }
    }
    public int getRandomWithExclusion(int start, int end, int... exclude) {
        int random=start+(int)(Math.random() * ((end - start) + 1));
        Arrays.sort(exclude);
        for(int e:exclude)
        {
            if(random<e){
                break;
            }
            else if((random==e)&&(random!=8))
            {
                random++;
            }
            else if((random==e)&&(random==8))
            {
                random=5;
                for(int f:exclude)
                {
                    if(random<f)
                    {
                        break;
                    }
                    else if(random==f)
                    {
                        random++;
                    }
                }
                break;
            }
        }
        return random;
    }
}
