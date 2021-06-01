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
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

class NoteAdapter extends FirestoreRecyclerAdapter<Example_Item,NoteAdapter.NoteHolder> {
    private OnItemClickListener listener;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Example_Item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Example_Item model) {
        holder.textView.setText(model.getfirstName());
        if(model.getphotoURL()==null){

        }else {
            Picasso.get().load(model.getphotoURL()).into(holder.imageView);
        }
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,
                parent, false);
        return new NoteHolder(v);

    }

    class NoteHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.displayImage);
            textView=itemView.findViewById(R.id.textDisplayName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}