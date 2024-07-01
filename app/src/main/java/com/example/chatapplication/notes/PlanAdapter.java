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
import com.example.chatapplication.Video;
import com.example.chatapplication.chat.ChatAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder>{
    private List<Plan> list=new ArrayList<>();
    private DatabaseReference mDataBase;
    public  int coun;
    public String keyP;
    public PlanAdapter(List<Plan>plan,Context context) {
        this.context = context;
        this.list=plan;
        mDataBase = FirebaseDatabase.getInstance().getReference().child("plan");
    }

    Context context;
    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanAdapter.PlanViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyc_plan_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        Plan plan=list.get(position);
        holder.bind(plan);
        holder.check.setChecked(plan.execution);
        holder.time.setText(plan.nowTime);
        holder.text.setText(plan.content);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                Plan currentPlan = list.get(adapterPosition);
                String newContent=holder.text.getText().toString();
                boolean newExecution = holder.check.isChecked();
                // ItemModel it=new ItemModel(newExecution);
                currentPlan.setContent(newContent);
                currentPlan.setExecution(newExecution);
                int planId = currentPlan.getKey();
               // String planKey = currentPlan.getKey();
               // String keyToUpdate = plan.getKey();adapterPosition
                mDataBase.child(String.valueOf(planId)).setValue(currentPlan)
                        .addOnSuccessListener(aVoid -> {
                            // Handle success
                            Toast.makeText(context, "Plan updated successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                            Toast.makeText(context, "Failed to update plan.", Toast.LENGTH_SHORT).show();
                        });



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class PlanViewHolder extends RecyclerView.ViewHolder
    {
        TextView time;
        EditText text;
        CheckBox check;
        ImageView imgEdit;
        String planKey;
       public PlanViewHolder(@NonNull View itemView){
           super(itemView);
           text=itemView.findViewById(R.id.text);
           check=itemView.findViewById(R.id.check);
           time=itemView.findViewById(R.id.time);
           imgEdit=itemView.findViewById(R.id.imgEdit);

       }
        public void bind(Plan plan) {
            // Привязываем данные объекта Plan к ViewHolder
            time.setText(plan.nowTime);
            text.setText(plan.content);
            check.setChecked(plan.execution);

        }
    }
}
