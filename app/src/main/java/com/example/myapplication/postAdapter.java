package com.example.myapplication;

import android.media.Image;
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
import com.google.firebase.firestore.SnapshotMetadata;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static com.example.myapplication.R.drawable.profileperson;

public class postAdapter extends FirestoreRecyclerAdapter<post_item,postAdapter.postHolder>  {
    private postAdapter.OnItemClickListener listener;
    public postAdapter(@NonNull FirestoreRecyclerOptions<post_item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull postHolder holder, int position, @NonNull post_item model) {

        Utils util = new Utils();
        holder.name.setText(model.getfirstName());
        holder.postText.setText(model.getmainPost());
        holder.created.setText(util.getTimeAgo(model.getcreatedAt()));
        if(model.getphotoURL()==null){

        }else {
            Picasso.get().load(model.getphotoURL()).into(holder.imageView);
        }


    }

    @NonNull
    @Override
    public postHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,
                parent, false);
        return new postHolder(v);

    }

    class postHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView created;
        TextView postText;
        ImageView imageView;
        ImageView imageView2;

        public postHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textDisplayNamePost);
            created=itemView.findViewById(R.id.textCreatedAt);
            postText=itemView.findViewById(R.id.postText);
            imageView=itemView.findViewById(R.id.displayImagePost);
            imageView2=itemView.findViewById(R.id.likeBtn);

            imageView2.setOnClickListener(new View.OnClickListener() {
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
    public void setOnItemClickListener(postAdapter.OnItemClickListener listener) {
        this.listener = listener;

    }
}
