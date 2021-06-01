package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button signUpbtn;
    Button LoginBtn;
    EditText Email;
    EditText password;
    FirebaseAuth Firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent = new Intent(this,MainContent.class);
            startActivity(intent);
            this.finish();
        }
        signUpbtn=findViewById(R.id.SignUpButton);
        LoginBtn=findViewById(R.id.LoginButton);
        Email=findViewById(R.id.inputEmailId);
        password=findViewById(R.id.inputPasswordLogin);
        Firebaseauth= FirebaseAuth.getInstance();
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString().isEmpty()){
                    Email.setHintTextColor(Color.RED);
                    Email.setHint("Enter Email");
                }
                if(password.getText().toString().length()<8){
                    password.setText(null);
                    password.setHintTextColor(Color.RED);
                    password.setHint("Enter at least 8 characters");
                }
                else {
                    Firebaseauth.signInWithEmailAndPassword(Email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainContent.class));
                                Toast.makeText(LoginActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                password.setText(null);
                                password.setHintTextColor(Color.RED);
                                password.setHint("Wrong Password");
                            }
                        }
                    });
                }
            }
        });
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}