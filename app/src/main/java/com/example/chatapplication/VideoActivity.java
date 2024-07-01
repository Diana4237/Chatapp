package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
private String UserVideo="";
    private String UrlVideo="";
    private String NameVideo="";
    ImageView imageView;
    private final List<Video> result=new ArrayList<>();
    private List<String> listProducts=new ArrayList<>();
    ImageView backBtn;
    ImageView acc;
private TextView nameUser;
private TextView nameV;
    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        UserVideo = intent.getStringExtra("videoItem");
        UrlVideo=intent.getStringExtra("videourl");
        NameVideo=intent.getStringExtra("nameV");
        nameUser=findViewById(R.id.avtorVideo);
        imageView=findViewById(R.id.img);
        nameV=findViewById(R.id.nameV);
        backBtn=findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        acc=findViewById(R.id.acc);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(VideoActivity.this,Account.class);
                intent2.putExtra("loginUser",UserVideo);
                startActivity(intent2);

            }
        });
        Picasso.get().load(UrlVideo).resize(350, 180)
                .centerCrop().into(imageView);
        nameUser.setText(UserVideo);
        nameV.setText(NameVideo);
        final RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(VideoActivity.this));


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


                        Video resultVideo=new Video(getnameVideo,getTime,"oven",getName,getUrlVideo,getMilk,getMeat,getCream,getFruit,getVeget,getYogurt,getCheese,getEgg,getBerrie,"video");
                        result.add(resultVideo);
                    }


                }
                LentaAdapter.OnVideoClick videoClick=new LentaAdapter.OnVideoClick() {
                    @Override
                    public void onVideoClick(Video video, int position) {
                        Intent intent = new Intent(VideoActivity.this, VideoActivity.class);
                        intent.putExtra("videoItem", video.loginUser);//отвечает за передачу данных
                        intent.putExtra("videourl",video.urlVideo);
                        intent.putExtra("nameV",video.nameVideo);
                        startActivity(intent);
                    }
                };
                recyclerView.setAdapter(new LentaAdapter(result, VideoActivity.this,videoClick));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }});
    }
}