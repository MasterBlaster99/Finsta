package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

public class createPostText extends AppCompatActivity {
String Uname;
String photoURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_text);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle("Create Post");
        setSupportActionBar(toolbar);
        FirebaseAuth Firebaseauth= FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID=Firebaseauth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();;
        EditText postET = findViewById(R.id.postEditText);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Uname=document.get("firstName").toString();
                    } else {
                    }
                } else {
                }
            }
        });
        Button btn = findViewById(R.id.PostBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(postET.getText().toString().isEmpty()){
                  postET.setHintTextColor(Color.WHITE);
                  postET.setHint("type something to post");
              }else {
                  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                  photoURL = user.getPhotoUrl().toString();
                  Map<String, Object> map = new HashMap<>();
                  map.put("createdAt", System.currentTimeMillis());
                  map.put("mainPost", postET.getText().toString());
                  map.put("photoURL", photoURL);
                  map.put("Uid", userID);
                  map.put("firstName", Uname);
                  map.put("likes", 0);
                  map.put("likedBy", "");
                  Task<Void> documentReference = db.collection("posts").document().set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Toast.makeText(createPostText.this, "Post created", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(getApplicationContext(), MainContent.class);
                          startActivity(intent);
                      }
                  });
              }
               }

        });

    }

}