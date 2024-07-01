package com.example.chatapplication.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapplication.AddVideo;
import com.example.chatapplication.LentaAdapter;
import com.example.chatapplication.LentaVideo;
import com.example.chatapplication.MainActivity;
import com.example.chatapplication.R;
import com.example.chatapplication.Video;
import com.example.chatapplication.VideoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanDayActivity extends AppCompatActivity {
private ProgressBar bar;
private Boolean dataExists;
private TextView tProc;
    private final List<Plan> result=new ArrayList<>();
    private  String loginUser="";
    private String passwordText="";
     private Button addVideo;
    private Button lentaVideo;
    private Button mess;
    private ImageView addItem;
    private ImageView timerBtn;
    private ImageView calendar;
    private DatabaseReference mDataBase;
    RecyclerView recyclerView;
    Boolean datapic;
    PlanAdapter lentaAdapter;

    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_day);
        Intent intent = getIntent();

        loginUser = intent.getStringExtra("login");
        passwordText=intent.getStringExtra("password");

         recyclerView = findViewById(R.id.listPlans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlanDayActivity.this));
        datapic=true;

        bar=findViewById(R.id.bar);
        mDataBase = FirebaseDatabase.getInstance().getReference("plan");


        addItem=findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDayActivity.this, AddItem.class);
                intent.putExtra("login", loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);
            }
        });

        addVideo =findViewById(R.id.addVideo);
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDayActivity.this, AddVideo.class);
                intent.putExtra("login", loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);

            }
        });
        lentaVideo=findViewById(R.id.lentaVideo);
        lentaVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDayActivity.this, LentaVideo.class);
                startActivity(intent);
            }
        });
        mess=findViewById(R.id.mess);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlanDayActivity.this, MainActivity.class);
                intent.putExtra("login",loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);
            }
        });
        tProc=findViewById(R.id.tProc);


        timerBtn=findViewById(R.id.timerBtn);
        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                Calendar midnight = Calendar.getInstance();
                midnight.add(Calendar.DAY_OF_MONTH, 1);
                midnight.set(Calendar.HOUR_OF_DAY, 0);
                midnight.set(Calendar.MINUTE, 0);
                midnight.set(Calendar.SECOND, 0);
                midnight.set(Calendar.MILLISECOND, 0);

                // Вычисляем разницу в миллисекундах
                long millisecondsLeft = midnight.getTimeInMillis() - now.getTimeInMillis();

                // Преобразуем миллисекунды в часы, минуты и секунды
                long hours = millisecondsLeft / (1000 * 60 * 60);
                long minutes = (millisecondsLeft / (1000 * 60)) % 60;
                long seconds = (millisecondsLeft / 1000) % 60;

                String mes="День не бесконечен! До конца дня "+ hours + " часов " + minutes + " минут " + seconds + " секунд";
                Toast.makeText(PlanDayActivity.this,mes,Toast.LENGTH_SHORT).show();
              }
        });

        calendar=findViewById(R.id.calendarBtn);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    DialogFragment newFragment = new CalendarFragment();

                Bundle args = new Bundle();
                args.putString("login", loginUser);
                args.putString("password",passwordText);
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });


    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            int countTrue = 0;
            int countAll = 0;

            result.clear();
            for (DataSnapshot plans : snapshot.child("plan").getChildren()) {

                long currentTimeMillis = System.currentTimeMillis();
                long moscowTimeMillis = currentTimeMillis + (3 * 60 * 60 * 1000);
                Date moscowTime = new Date(moscowTimeMillis);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate= sdf.format(moscowTime);
                    //Toast.makeText(PlanDayActivity.this,"Нет данных на это число "+currentDate,Toast.LENGTH_SHORT).show();



                if (plans.hasChild("user")) {
                    final String getUser = plans.child("user").getValue(String.class);
                    final String getContent = plans.child("content").getValue(String.class);
                    final String getTime = plans.child("nowTime").getValue(String.class);
                    final Boolean getExecution = plans.child("execution").getValue(Boolean.class);
                    final String getDate = plans.child("nowDate").getValue(String.class);
                    final int getKey = plans.child("key").getValue(int.class);

                    Plan resultPlans = new Plan(getUser, getContent, getExecution, getDate, getTime, getKey);
                     if (getUser.equals(loginUser) && getDate.equals(currentDate)) {

                        result.add(resultPlans);
                        countAll++;
                        if (getExecution.equals(true)) {
                            countTrue++;
                        }
                    }

                }
            }
             lentaAdapter = new PlanAdapter(result, PlanDayActivity.this);
            lentaAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(lentaAdapter);
            int progressValue = (int) ((float) countTrue / countAll * 100); // Значение прогресса (от 0 до 100)result.size()
            bar.setProgress(progressValue);
            tProc.setText(progressValue + "%");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    }
    private int getCheckedCount(ViewGroup viewGroup) {
        int checkedCount = 0;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof CheckBox && ((CheckBox) view).isChecked()) {
                checkedCount++;
            }
        }
        return checkedCount;
    }
}