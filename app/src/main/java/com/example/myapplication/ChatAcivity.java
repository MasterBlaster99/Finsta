package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class ChatAcivity extends AppCompatActivity {
    EditText editText ;
    String photoURL;
    messageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_acivity);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        editText=findViewById(R.id.inputMessage);
        FloatingActionButton sendBtn = findViewById(R.id.sendMsgBtn);
        FirebaseAuth Firebaseauth;
        Firebaseauth= FirebaseAuth.getInstance();
        String userID=Firebaseauth.getCurrentUser().getUid();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getPhotoUrl()==null){

        }else {
            photoURL = user.getPhotoUrl().toString();
        }
        DocumentReference docRef = db.collection("users").document(userID);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = db.collection("messages").document();
                Map<String, Object> map = new HashMap<>();
                cryptos encryption =new cryptos();
                map.put("message",encryption.encryptString(editText.getText().toString()));
                map.put("photoURL",photoURL);
                map.put("Uid",userID);
                map.put("sentAt",System.currentTimeMillis());
                documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       editText.setText(null);
                    }
                });
            }
        });
        Query query = FirebaseFirestore.getInstance().collection("messages").orderBy("sentAt",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<messageItem> options = new FirestoreRecyclerOptions.Builder<messageItem>().setQuery(query, messageItem.class).build();
        adapter=new messageAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}