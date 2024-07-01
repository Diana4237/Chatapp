package com.example.chatapplication.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapplication.AddVideo;
import com.example.chatapplication.LentaVideo;
import com.example.chatapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddItem extends AppCompatActivity {
    private  String loginUser="";
    private String passwordText="";
    private Long countFir;
    private boolean flag = true;

    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");

    private Button addIt;
private EditText editIt;
    private String USER_KEY = "plan";
    private List<String> items=new ArrayList<>();
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent intent = getIntent();
        loginUser = intent.getStringExtra("login");
        passwordText=intent.getStringExtra("password");
        editIt=findViewById(R.id.editIt);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        items.clear();
         addIt=findViewById(R.id.addIt);
         addIt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String newItem = editIt.getText().toString().trim();
                 if (newItem.isEmpty()) { // Проверка на пустую строку
                     Toast.makeText(AddItem.this, "Введите пункт плана", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 long currentTimeMillis = System.currentTimeMillis();
                 long moscowTimeMillis = currentTimeMillis + (3 * 60 * 60 * 1000);
                 Date moscowTime = new Date(moscowTimeMillis);
                 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                 SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm:ss");
                 String currentTime = sdf2.format(moscowTime);
                 String currentDate=sdf.format(moscowTime);


                // items.add(newItem);
                Plan plan=new Plan();
                plan.setUser(loginUser);
                plan.setContent(newItem);
                plan.setExecution(false);
                plan.setNowDate(currentDate);
                plan.setNowTime(currentTime);
                //plan.setKey();

//новая попытка
                 databaseReference.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         while(flag){
                         long count = snapshot.child("plan").getChildrenCount();
                         Toast.makeText(AddItem.this, "Количество записей: " + count, Toast.LENGTH_SHORT).show();
                         countFir=count;
                             flag=false;
                             plan.setKey(Math.toIntExact(countFir));
                             Add(plan, AddItem.this, Math.toIntExact(countFir));
                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
                 //конец


                // String newKey = mDataBase.push().getKey();
                // mDataBase.push().setValue(plan);
                // Toast.makeText(AddItem.this,newKey,Toast.LENGTH_SHORT);
                 Intent intent = new Intent(AddItem.this, PlanDayActivity.class);
                 intent.putExtra("login", loginUser);
                 intent.putExtra("password",passwordText);
                 startActivity(intent);
             }
         });


    }
    public void Add(Plan plan, Context context,int adapterPosition){

     mDataBase.child(String.valueOf(adapterPosition)).setValue(plan)
                        .addOnSuccessListener(aVoid -> {
        // Handle success
        Toast.makeText(context, "Plan updated successfully!", Toast.LENGTH_SHORT).show();
    })
            .addOnFailureListener(e -> {
        // Handle failure
        Toast.makeText(context, "Failed to update plan.", Toast.LENGTH_SHORT).show();
    });
    }

}
