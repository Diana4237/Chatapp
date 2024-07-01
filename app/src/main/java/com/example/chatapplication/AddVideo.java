package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.chatapplication.notes.PlanDayActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddVideo extends AppCompatActivity {
    private  String loginUser="";
    private String passwordText="";
    private String USER_KEY = "video";
    private DatabaseReference mDataBase;
private EditText nameVid;
    private EditText edit;
private Button publish;
   // private Button addVideo;
    private Button lentaVideo;
    private Button mess;
    private Button plan;

private RadioButton oven;
private RadioButton unbaking;
private RadioButton micro;
private CheckBox milk;
    private CheckBox meat;
    private CheckBox cream;
    private CheckBox fruit;
    private CheckBox veget;
    private CheckBox yogurt;
    private CheckBox cheese;
    private CheckBox egg;
    private CheckBox berrie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        Intent intent = getIntent();
        loginUser = intent.getStringExtra("login");
       // listPerehod=findViewById(R.id.listPerehod);
        passwordText=intent.getStringExtra("password");

        lentaVideo=findViewById(R.id.lentaVideo);
        lentaVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddVideo.this, LentaVideo.class);
                startActivity(intent);
            }
        });
        mess=findViewById(R.id.mess);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddVideo.this, MainActivity.class);
                intent.putExtra("login",loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);
            }
        });
        plan=findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddVideo.this, PlanDayActivity.class);
                intent.putExtra("login", loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);
            }
        });



        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        publish=findViewById(R.id.publish);
        edit=findViewById(R.id.edit);
        nameVid=findViewById(R.id.nameVid);
        milk=findViewById(R.id.milk);
        berrie=findViewById(R.id.berrie);
        meat=findViewById(R.id.meat);
        cream=findViewById(R.id.cream);
        fruit=findViewById(R.id.fruit);
        veget=findViewById(R.id.veget);
        yogurt=findViewById(R.id.yogurt);
        cheese=findViewById(R.id.cheese);
        egg=findViewById(R.id.egg);

        micro=findViewById(R.id.micro);
        unbaking=findViewById(R.id.unbaking);
        oven=findViewById(R.id.oven);

      /*  listPerehod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddVideo.this, LentaVideo.class);

                startActivity(intent);
            }
        });*/


        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkBoxMilk=milk.isChecked();
                Boolean checkBoxMeat=meat.isChecked();
                Boolean checkBoxCream=cream.isChecked();
                Boolean checkBoxYogurt=yogurt.isChecked();
                Boolean checkBoxFruit=fruit.isChecked();
                Boolean checkBoxVeget=veget.isChecked();
                Boolean checkBoxCheese=cheese.isChecked();
                Boolean checkBoxEgg=egg.isChecked();
                Boolean checkBoxBerrie=berrie.isChecked();
                String url="";
                if(edit.getText().toString()!=null&&edit.getText().toString().trim().length() > 0)
                { url = edit.getText().toString();}
                else{
                    url ="https://cdn-icons-png.flaticon.com/512/2088/2088093.png";
                }
                String nameV="";
                if(nameVid.getText().toString()!=null&&nameVid.getText().toString().trim().length()>0)
                {
                    nameV=nameVid.getText().toString();
                }
                else{nameV=" ";}
                long currentTimeMillis = System.currentTimeMillis();
                long moscowTimeMillis = currentTimeMillis + (3 * 60 * 60 * 1000);
                Date moscowTime = new Date(moscowTimeMillis);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String currentTime = sdf.format(moscowTime);
                String way="";

                if(oven.isChecked()==true){
                    way="Oven";
                }
                if(unbaking.isChecked()==true){
                    way="Without baking";
                }
                if(micro.isChecked()==true){
                    way="Microwave";
                }

                String id = mDataBase.getKey();
                // String newKey = mDatabase.push().getKey();
                Video video = new Video(nameV,currentTime,way,loginUser,url,checkBoxMilk,checkBoxMeat,checkBoxCream,checkBoxFruit,checkBoxVeget,checkBoxYogurt,checkBoxCheese,checkBoxEgg,checkBoxBerrie, id);
                mDataBase.push().setValue(video);

                Intent intent = new Intent(AddVideo.this, LentaVideo.class);
                intent.putExtra("login", loginUser);
                startActivity(intent);
            }
        });
    }
}