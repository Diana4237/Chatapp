package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.chatapplication.notes.PlanDayActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LentaVideo extends AppCompatActivity {
    private  String loginUser="";
    private String passwordText="";
private final List<Video> result=new ArrayList<>();
private SearchView searchVideo;
private LentaAdapter lentaAdapter;
     private Button addVideo;
   // private Button lentaVideo;
    private Button mess;
    private Button plan;

private final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenta_video);

        loginUser=getIntent().getStringExtra("login");
        passwordText=getIntent().getStringExtra("password");

        addVideo =findViewById(R.id.addVideo);
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LentaVideo.this, AddVideo.class);
                intent.putExtra("login", loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);

            }
        });
        mess=findViewById(R.id.mess);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LentaVideo.this, MainActivity.class);
                intent.putExtra("login",loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);
            }
        });
        plan=findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LentaVideo.this, PlanDayActivity.class);
                intent.putExtra("login", loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);
            }
        });

        searchVideo=findViewById(R.id.search);
        searchVideo.clearFocus();
        searchVideo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        final RecyclerView recyclerView = findViewById(R.id.lenta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(LentaVideo.this));


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

                Video resultVideo=new Video(getnameVideo, getTime,"oven",getName,getUrlVideo,getMilk,getMeat,getCream,getFruit,getVeget,getYogurt,getCheese,getEgg,getBerrie,"video");
                result.add(resultVideo);
            }


        }
        LentaAdapter.OnVideoClick videoClick=new LentaAdapter.OnVideoClick() {
            @Override
            public void onVideoClick(Video video, int position) {
                Intent intent = new Intent(LentaVideo.this, VideoActivity.class);
                intent.putExtra("videoItem", video.loginUser);//отвечает за передачу данных
                intent.putExtra("videourl",video.urlVideo);
                intent.putExtra("nameV",video.nameVideo);
                startActivity(intent);
            }
        };
         lentaAdapter=new LentaAdapter(result, LentaVideo.this,videoClick);
        recyclerView.setAdapter(lentaAdapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


        }
    private void filterList(String text) {
        List<Video> filteredList=new ArrayList<>();
        for(Video video:result)
        {
            if(video.getNameVideo().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(video);
            }
        }
        if(filteredList.isEmpty())
        {
            Toast.makeText(this,"No data found",Toast.LENGTH_SHORT);
        }
        else
        {
            lentaAdapter.setFilteredVideo(filteredList,LentaVideo.this);
        }
    }



}