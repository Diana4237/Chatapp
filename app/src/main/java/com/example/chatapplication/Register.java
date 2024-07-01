package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText name=findViewById(R.id.r_login);
        final EditText password=findViewById(R.id.r_password);
        final AppCompatButton registerBtn=findViewById(R.id.r_registerBtn);


if(!MemoryData.getData(this).isEmpty())
{
    Intent intent=new Intent(Register.this, MainActivity.class);
    intent.putExtra("login",MemoryData.getData(this));
    intent.putExtra("password",MemoryData.getName(this));
    startActivity(intent);
    finish();
}

        registerBtn.setOnClickListener(new View.OnClickListener(){


            @Override
public void onClick(View v){
               // progressDialog.show();
final String nameText=name.getText().toString();
final String passwordText=password.getText().toString();


     if(nameText.isEmpty()||passwordText.isEmpty())
     {
        // progressDialog.dismiss();
    Toast.makeText(Register.this,"All fieldrequired!",Toast.LENGTH_SHORT).show();
     }
     else {
         databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                // progressDialog.dismiss();
                 if(snapshot.child("users").hasChild(nameText)){
                     Toast.makeText(Register.this,"Login already exists",Toast.LENGTH_SHORT).show();
                 }
                 else {
                 databaseReference.child("users").child(nameText).child("password").setValue(passwordText);
                 databaseReference.child("users").child(nameText).child("profile_pic").setValue("");
                MemoryData.saveData(nameText,Register.this);
MemoryData.saveName(passwordText,Register.this);


                 Toast.makeText(Register.this,"Succes!",Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(Register.this, MainActivity.class);
                 intent.putExtra("login",nameText);
                 intent.putExtra("password",passwordText);
                 startActivity(intent);
                 finish();
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
               //  progressDialog.dismiss();
             }
         });
     }
}

        });
    }
}