package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class viewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView personName;

    public viewHolder(@NonNull View itemView){
        super(itemView);
    }
    public void setItem(FragmentActivity activity,String name, String imgUrl){
       imageView=itemView.findViewById(R.id.displayImage);
       personName=itemView.findViewById(R.id.textDisplayName);
        Picasso.get().load(imgUrl).into(imageView);
        personName.setText(name);

    }
}
