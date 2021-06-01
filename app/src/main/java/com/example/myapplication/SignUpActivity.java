package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    Button SignUpBtn;
    EditText firstName;
    EditText lastName;
    EditText Email;
    EditText password;
    EditText rePassword;
    FirebaseAuth Firebaseauth;
    String userID;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignUpBtn=findViewById(R.id.SignUpButton);
        firstName=findViewById(R.id.FirstName);
        lastName=findViewById(R.id.LastNAme);
        Email=findViewById(R.id.inputEmailId);
        password=findViewById(R.id.inputPasswordSignUp);
        rePassword=findViewById(R.id.ReEnterPassword);
        Firebaseauth= FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if(firstName.getText().toString().isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    firstName.setText(null);
                    firstName.setHintTextColor(Color.RED);
                    firstName.setHint("Enter First Name");
                }
                if(Email.getText().toString().isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    Email.setText(null);
                    Email.setHintTextColor(Color.RED);
                    Email.setHint("Enter Email ID");
                }
                if(!Email.getText().toString().contains("@")){
                    progressBar.setVisibility(View.GONE);
                    Email.setText(null);
                    Email.setHintTextColor(Color.RED);
                    Email.setHint("Please enter a valid Email ID");
                }
                if(!Email.getText().toString().contains(".com")){
                    progressBar.setVisibility(View.GONE);
                    Email.setText(null);
                    Email.setHintTextColor(Color.RED);
                    Email.setHint("Please enter a valid Email ID");
                }
                if(lastName.getText().toString().isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    lastName.setText(null);
                    lastName.setHintTextColor(Color.RED);
                    lastName.setHint("Enter Last Name");
                }
                if(password.getText().toString().length()<8){
                    progressBar.setVisibility(View.GONE);
                    password.setText(null);
                    password.setHintTextColor(Color.RED);
                    password.setHint("enter at least 8 characters");
                }
                if(rePassword.getText().toString().length()<8){
                    progressBar.setVisibility(View.GONE);
                    rePassword.setText(null);
                    rePassword.setHintTextColor(Color.RED);
                    rePassword.setHint("enter at least 8 characters");
                }
                if(!password.getText().toString().equals(rePassword.getText().toString())){
                    progressBar.setVisibility(View.GONE);
                    rePassword.setText(null);
                    rePassword.setHintTextColor(Color.RED);
                    rePassword.setHint("Do not match");
                }
                if(password.getText().toString().equals(rePassword.getText().toString()) && Email.getText().toString().contains("@") && Email.getText().toString().contains(".com")&& !firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()) {
                    Firebaseauth.createUserWithEmailAndPassword(Email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                        intent.putExtra("firstName",firstName.getText().toString());
                                        intent.putExtra("lastName",lastName.getText().toString());
                                        finish();
                                        startActivity(intent);


                                        userID = Firebaseauth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fstore.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("firstName", firstName.getText().toString());
                                        user.put("lastName", lastName.getText().toString());
                                        user.put("Email", Email.getText().toString());
                                        user.put("password", password.getText().toString());
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "onsuccess: user profile is created for" + userID);
                                            }
                                        });
                                        Toast.makeText(SignUpActivity.this, "successfully signed up", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "sign up failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}