package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatapplication.messages.MessagesAdapter;
import com.example.chatapplication.messages.MessagesList;
import com.example.chatapplication.notes.PlanDayActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
private final List<MessagesList> messagesLists=new ArrayList<>();

    private String login;
private String password;
private RecyclerView messagesRecyclerView;
    private int unseenMessages=0;
   private String lastMessage="";
   private String chatKey="";
   private boolean dataSet=false;
   private MessagesAdapter messagesAdapter;
   private Button addVideo;
   private Button lentaVideo;
   //private Button mess;
   private Button plan;
DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleImageView userProfilePic=findViewById(R.id.userProfilePic);

       messagesRecyclerView=findViewById(R.id.messagesRecyclerView);
        login=getIntent().getStringExtra("login");
        password=getIntent().getStringExtra("password");

        addVideo =findViewById(R.id.addVideo);
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddVideo.class);
                intent.putExtra("login", login);
                intent.putExtra("password",password);
                startActivity(intent);

            }
        });

        lentaVideo=findViewById(R.id.lentaVideo);
        lentaVideo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this, LentaVideo.class);
              intent.putExtra("login", login);
              intent.putExtra("password",password);
              startActivity(intent);
          }
        });
        /*mess=findViewById(R.id.mess);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LentaVideo.this, MainActivity.class);
                intent.putExtra("login",loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);
            }
        });*/
        plan=findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanDayActivity.class);
                intent.putExtra("login", login);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });



        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter to recycler
        messagesAdapter=new MessagesAdapter(messagesLists,MainActivity.this);
        messagesRecyclerView.setAdapter(messagesAdapter);

        //
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String profilePicUrl=snapshot.child("users").child(login).child("profile_pic").getValue(String.class);

           if(!profilePicUrl.isEmpty()){
               Picasso.get().load(profilePicUrl).into(userProfilePic);
           }


            progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesLists.clear();
                unseenMessages=0;
                lastMessage="";
                chatKey="";

                for(DataSnapshot dataSnapshot:snapshot.child("users").getChildren())
                {
                 final String getMobile=dataSnapshot.getKey();
                    dataSet=false;

                  if(!getMobile.equals(login))
                  {
                   final String getPassword=dataSnapshot.child("password").getValue(String.class);
                   final String getProfilePic=dataSnapshot.child("profile_pic").getValue(String.class);



databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        int getChatCounts=(int)snapshot.getChildrenCount();
        if(getChatCounts>0)
        {
            for(DataSnapshot dataSnapshot1:snapshot.getChildren())
            {
                final String getKey=dataSnapshot1.getKey();
                chatKey=getKey;

                if(dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messages"))
                {
                    final String getUserOne=dataSnapshot1.child("user_1").getValue(String.class);
                    final String getUserTwo=dataSnapshot1.child("user_2").getValue(String.class);


                    if((getUserOne.equals(getMobile)&&getUserTwo.equals(login))||(getUserOne.equals(login) && getUserTwo.equals(getMobile)))
                    {
                        for(DataSnapshot chatDataSnapshot:dataSnapshot1.child("messages").getChildren())
                        {
                            final long getMessageKey=Long.parseLong(chatDataSnapshot.getKey());
                            final long getLastSeenMessage= Long.parseLong(MemoryData.getLastMsgTS(MainActivity.this,getKey));
                            lastMessage=chatDataSnapshot.child("msg").getValue(String.class);
                            if(getMessageKey>getLastSeenMessage)
                            {
                                unseenMessages++;
                            }
                        }
                    }


                }
            }
        }
        if(!dataSet)
        {
            dataSet=true;
            MessagesList messagesList=new MessagesList(getPassword,getMobile,lastMessage,getProfilePic,unseenMessages,chatKey);
            messagesLists.add(messagesList);
            messagesAdapter.updateData(messagesLists);
        }


       // messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists,MainActivity.this));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
                  //
               }
                }
                //
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}