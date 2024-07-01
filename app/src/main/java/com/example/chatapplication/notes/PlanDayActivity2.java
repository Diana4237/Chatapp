package com.example.chatapplication.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.chatapplication.LentaVideo;
import com.example.chatapplication.MainActivity;
import com.example.chatapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanDayActivity2 extends AppCompatActivity {

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
    PlanAdapterCalend lentaAdapter;

    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapplication-f4c66-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_day2);
        Intent intent = getIntent();
        loginUser = intent.getStringExtra("login");
        passwordText=intent.getStringExtra("password");
        recyclerView = findViewById(R.id.listPlans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlanDayActivity2.this));
        datapic=true;
        int year = intent.getIntExtra("selectedYear", -1);
        int month = intent.getIntExtra("selectedMonth", -1);
        int day = intent.getIntExtra("selectedDay", -1);


        bar=findViewById(R.id.bar);
        mDataBase = FirebaseDatabase.getInstance().getReference("plan");




        addVideo =findViewById(R.id.addVideo);
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDayActivity2.this, AddVideo.class);
                intent.putExtra("login", loginUser);
                intent.putExtra("password",passwordText);
                startActivity(intent);

            }
        });
        lentaVideo=findViewById(R.id.lentaVideo);
        lentaVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDayActivity2.this, LentaVideo.class);
                startActivity(intent);
            }
        });
        mess=findViewById(R.id.mess);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlanDayActivity2.this, MainActivity.class);
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
                Toast.makeText(PlanDayActivity2.this,mes,Toast.LENGTH_SHORT).show();
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
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                Date moscow = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate= sdf.format(moscow);
                result.clear();
                for (DataSnapshot plans : snapshot.child("plan").getChildren()) {

                    if((year != -1 && month != -1 && day != -1)){
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
                }

                if (result.isEmpty()) {
                    // Если список пуст, переходим на другое окно
                    Intent intent = new Intent(PlanDayActivity2.this, PlanDayActivity.class);
                    intent.putExtra("login",loginUser);
                    intent.putExtra("password",passwordText);
                    startActivity(intent);
                } else {
                    // Если список не пуст, обновляем адаптер
                    lentaAdapter = new PlanAdapterCalend(result, PlanDayActivity2.this);
                    recyclerView.setAdapter(lentaAdapter);
                    int progressValue = (int) ((float) countTrue / countAll * 100); // Значение прогресса (от 0 до 100)result.size()
                    bar.setProgress(progressValue);
                    tProc.setText(progressValue + "%");
                }



               // lentaAdapter = new PlanAdapter(result, PlanDayActivity2.this);

               // recyclerView.setAdapter(lentaAdapter);

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