package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


public class messageAdapter extends FirestoreRecyclerAdapter<messageItem,messageAdapter.messageHolder> {
    public static final int LEFT=0;
    public static final int RIGHT=1;
    FirebaseAuth Firebaseauth= FirebaseAuth.getInstance();
    String userID=Firebaseauth.getCurrentUser().getUid();

    public messageAdapter(@NonNull FirestoreRecyclerOptions<messageItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull messageAdapter.messageHolder holder, int position, @NonNull messageItem model) {
        cryptos cryptos=new cryptos();

        if(userID.equals(model.getUid())){
            holder.msgSend.setText(cryptos.decryptString(model.getmessage()));
            holder.msgRec.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.msgRec.setText(cryptos.decryptString(model.getmessage()));
            if(model.getphotoURL()==null){

            }else {
                Picasso.get().load(model.getphotoURL()).into(holder.imageView);
            }
            holder.msgSend.setVisibility(View.GONE);
        }
    }
    @NonNull
    @Override
    public messageAdapter.messageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_row,
                    parent, false);
            return new messageAdapter.messageHolder(v);
    }

    class messageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView msgRec;
        TextView msgSend;

        public messageHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.profile_image_leftChat);
            msgRec=itemView.findViewById(R.id.msgReciever);
            msgSend=itemView.findViewById(R.id.msgSender);
        }
    }
}
