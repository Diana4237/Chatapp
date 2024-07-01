package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.core.Context;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LentaAdapter extends RecyclerView.Adapter<LentaAdapter.LentaViewHolder> {

    //private int position;
    private OnVideoClick onClickListner;
    private android.content.Context context;
    private List<Video> list;
    private List<String> listProducts;
    Boolean berrie;
    // holder.ber.setText(berrie.toString());
    Boolean milk;
    Boolean cheese;
    Boolean cream;
    Boolean meat;
    Boolean egg;
    Boolean veget;
    Boolean fruit;
    Boolean yogurt;
private Spinner spinner;

    public LentaAdapter(List<Video> list, android.content.Context context,OnVideoClick onClickListner) {
        this.list = list;
        this.context=context;
        this.onClickListner=onClickListner;
        this.listProducts = new ArrayList<>();
    }

    public void setFilteredVideo(List<Video> videoList, android.content.Context context)
    {
        this.list=videoList;
        this.context=context;
        notifyDataSetChanged();
        this.listProducts = new ArrayList<>();
    }
    interface OnVideoClick{
        void onVideoClick(Video video,int position);
    }

    @NonNull
    @Override
    public LentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LentaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lenta_adapter,null));
    }

    @Override
    public void onBindViewHolder(@NonNull LentaViewHolder holder, int position) {
     Video video=list.get(position);
     listProducts.clear();
     holder.userName.setText(video.loginUser);
     holder.timePost.setText(video.timePost);
     holder.nameVid.setText(video.nameVideo);
     Picasso.get().load(video.urlVideo).resize(350, 180)
                .centerCrop().into(holder.imgVideo);
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             onClickListner.onVideoClick(video,holder.getAdapterPosition());
         }
     });

        berrie= video.berrie;
       // holder.ber.setText(berrie.toString());
         milk= video.milk;
        cheese= video.cheese;
        cream= video.cream;
        meat= video.meat;
        egg= video.egg;
        veget= video.veget;
        fruit= video.fruit;
        yogurt= video.yogurt;
        if(berrie.equals(true)){listProducts.add("Berries");}
        if(milk.equals(true)){listProducts.add("Milk");}
        if(cheese.equals(true)){listProducts.add("Cheese");}
        if(cream.equals(true)){listProducts.add("Cream");}
        if(meat.equals(true)){listProducts.add("Meat");}
        if(egg.equals(true)){listProducts.add("Egg");}
        if(veget.equals(true)){listProducts.add("Vegetables");}
        if(fruit.equals(true)){listProducts.add("Fruit");}
        if(yogurt.equals(true)){listProducts.add("Yogurt");}
        holder.listProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.spinner.getVisibility() == View.VISIBLE) {
                    holder.spinner.setVisibility(View.GONE);
                } else {
                    holder.spinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listProducts);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    holder.spinner.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LentaViewHolder extends RecyclerView.ViewHolder{
       private final TextView userName,timePost,nameVid;
       private final ImageView profilePic;
        private final ImageView imgVideo;
        private final ImageView listProd;
        private final Spinner spinner;
       public LentaViewHolder(View itemView){
       super(itemView);
           userName=itemView.findViewById(R.id.userName);
           timePost=itemView.findViewById(R.id.timePost);
            profilePic=itemView.findViewById(R.id.profilePic);
           imgVideo=itemView.findViewById(R.id.imgVideo);
           nameVid=itemView.findViewById(R.id.nameVid);
           spinner=itemView.findViewById(R.id.spinner);
           listProd=itemView.findViewById(R.id.listProd);
          // ber=itemView.findViewById(R.id.ber);
       }
   }

}