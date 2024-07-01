package com.example.chatapplication.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapplication.MemoryData;
import com.example.chatapplication.R;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");

    private List<ChatList> chatLists = new ArrayList<>();
    String chatKey;
    private String getUserLogin = "";
    private RecyclerView chattingRecyclerView;
    private ChatAdapter chatAdapter;
    private  boolean loadingFirstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView nameTV = findViewById(R.id.name);
        final EditText messageEditText = findViewById(R.id.messageEditTxt);
        final ImageView sendBtn = findViewById(R.id.sendBtn);
        chattingRecyclerView = findViewById(R.id.chattingRecyclerView);
        final CircleImageView profilePic = findViewById(R.id.profilePic);


        final String getLogin = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        chatKey = getIntent().getStringExtra("chat_key");


        //get user login from memory
        getUserLogin = MemoryData.getData(Chat.this);
        nameTV.setText(getLogin);
        Picasso.get().load(getProfilePic).into(profilePic);
        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        chatAdapter=new ChatAdapter(chatLists,Chat.this);
        chattingRecyclerView.setAdapter(chatAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }
                if(snapshot.hasChild("chat"))
                {
                    if(snapshot.child("chat").child(chatKey).hasChild("messages"))
                    {
                        chatLists.clear();
                       for(DataSnapshot messagesSnapshot:snapshot.child("chat").child(chatKey).child("messages").getChildren())
                       {
                           if(messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("login"))
                           {
                               final String messageTimestamps=messagesSnapshot.getKey();
                               final  String getLogin=messagesSnapshot.child("login").getValue(String.class);
                               final  String getMsg=messagesSnapshot.child("msg").getValue(String.class);
//

                               long currentTimeMillis = System.currentTimeMillis();
                               long moscowTimeMillis = currentTimeMillis + (3 * 60 * 60 * 1000);
                               Date moscowTime = new Date(moscowTimeMillis);
                               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
                               SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm:ss");
                               String currentTime = sdf2.format(moscowTime);
                               String currentDate=sdf.format(moscowTime);
                               //
                               Date date=new Date(Long.parseLong(messageTimestamps));
                               SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                               SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                               // ChatList chatList=new ChatList(getLogin,getMsg,simpleDateFormat.format(date),simpleTimeFormat.format(date));
                               ChatList chatList=new ChatList(getLogin,getMsg,currentDate,currentTime);
                               chatLists.add(chatList);
                               if(loadingFirstTime || Long.parseLong(messageTimestamps)>Long.parseLong(MemoryData.getLastMsgTS(Chat.this,chatKey)))
                               {
                                   loadingFirstTime=false;
                                   MemoryData.saveLastMsgTS(messageTimestamps, chatKey, Chat.this);
                                   chatAdapter.updateChatList(chatLists);

                                   chattingRecyclerView.scrollToPosition(chatLists.size()-1);
                               }
                           }

                       }
                    }
;                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                final String getTxtMessage = messageEditText.getText().toString();
                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserLogin);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getLogin);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("login").setValue(getUserLogin);

                messageEditText.setText("");
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}