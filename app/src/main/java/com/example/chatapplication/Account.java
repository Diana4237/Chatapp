package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapplication.ImageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Account extends AppCompatActivity {
    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");
    List<String> urlVideoList = new ArrayList<>();
    private GridView grid;
    private String LoginUser="";
    private TextView textName;
    private TextView texturl;
    private ImageView backBtn;
    private final List<Video> result=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


Intent intent=getIntent();
Bundle bundle=intent.getExtras();
LoginUser=bundle.getString("loginUser");
        textName=findViewById(R.id.textName);
        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textName.setText("Name user: "+LoginUser);
        texturl=findViewById(R.id.texturl);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                result.clear();
                for(DataSnapshot videos:snapshot.child("video").getChildren()){

                    if(videos.hasChild("loginUser") && videos.hasChild("timePost") )
                    {
                        final String getName=videos.child("loginUser").getValue(String.class);
                        final String getTime=videos.child("timePost").getValue(String.class);
                        final Boolean getMilk=videos.child("milk").getValue(Boolean.class);
                        final Boolean getMeat=videos.child("meat").getValue(Boolean.class);
                        final Boolean getCream=videos.child("cream").getValue(Boolean.class);
                        final Boolean getFruit=videos.child("fruit").getValue(Boolean.class);
                        final Boolean getVeget=videos.child("veget").getValue(Boolean.class);
                        final Boolean getYogurt=videos.child("yogurt").getValue(Boolean.class);
                        final Boolean getCheese=videos.child("cheese").getValue(Boolean.class);
                        final Boolean getEgg=videos.child("egg").getValue(Boolean.class);
                        final Boolean getBerrie=videos.child("berrie").getValue(Boolean.class);
                        final String getUrlVideo=videos.child("urlVideo").getValue(String.class);
                        final String getnameVideo=videos.child("nameVideo").getValue(String.class);
if(getName.equals(LoginUser)){
                        urlVideoList.add(getUrlVideo);}
                        Video resultVideo=new Video(getnameVideo,getTime,"oven",getName,getUrlVideo,getMilk,getMeat,getCream,getFruit,getVeget,getYogurt,getCheese,getEgg,getBerrie,"video");
                        result.add(resultVideo);
                    }


                   /* StringBuilder stringBuilder = new StringBuilder();
                    for (String string : urlVideoList) {
                        stringBuilder.append(string).append("\n");
                    }
                    texturl.setText(stringBuilder.toString());
if(texturl.getText()==""){
    texturl.setText("Nan");
}*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }});
        grid = (GridView)findViewById(R.id.gridView);
        ImageAdapter imageAdapter=new ImageAdapter(Account.this, urlVideoList);
        if (grid instanceof GridView) {
            grid.setAdapter(imageAdapter);
        }


    }

// Создайте класс ImageAdapter для адаптера GridView


}