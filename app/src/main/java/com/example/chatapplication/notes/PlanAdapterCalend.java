package com.example.chatapplication.notes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapterCalend extends RecyclerView.Adapter<PlanAdapterCalend.PlanCalendViewHolder>{
    private List<Plan> list=new ArrayList<>();
    private DatabaseReference mDataBase;
    public  int coun;
    public String keyP;
    public PlanAdapterCalend(List<Plan>plan, Context context) {
        this.context = context;
        this.list=plan;
        mDataBase = FirebaseDatabase.getInstance().getReference().child("plan");
    }

    Context context;
    @NonNull
    @Override
    public PlanAdapterCalend.PlanCalendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanAdapterCalend.PlanCalendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyc_plan_item_calend,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapterCalend.PlanCalendViewHolder holder, int position) {
        Plan plan=list.get(position);
        holder.bind(plan);
        holder.check.setChecked(plan.execution);
        holder.time.setText(plan.nowTime);
        holder.text.setText(plan.content);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class PlanCalendViewHolder extends RecyclerView.ViewHolder
    {
        TextView time,text;

        CheckBox check;
        String planKey;
        public PlanCalendViewHolder(@NonNull View itemView){
            super(itemView);
            text=itemView.findViewById(R.id.text);
            check=itemView.findViewById(R.id.check);
            time=itemView.findViewById(R.id.time);
            //imgEdit=itemView.findViewById(R.id.imgEdit);

        }
        public void bind(Plan plan) {
            // Привязываем данные объекта Plan к ViewHolder
            time.setText(plan.nowTime);
            text.setText(plan.content);
            check.setChecked(plan.execution);

        }
    }
}


