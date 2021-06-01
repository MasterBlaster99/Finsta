package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
EditText firstName;
EditText lastName;
TextView dateTV;
EditText bio;
DatePickerDialog.OnDateSetListener setListener;
FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
TextView addPhoto;
Button saveBtn;
private static int RequestCode = 123;
ImageView imageView;
ImageView zoomImageView;
FirebaseAuth Firebaseauth;
String userID;
ConstraintLayout constraintLayout;
String photoURL;
FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        constraintLayout=findViewById(R.id.constraintView);
        zoomImageView=findViewById(R.id.zoomImageView);
        Firebaseauth= FirebaseAuth.getInstance();
        firstName=findViewById(R.id.FirstNameEditText);
        lastName=findViewById(R.id.LastNameEditText);
        dateTV=findViewById(R.id.DateOfBirthEditText);
        addPhoto=findViewById(R.id.addPhotoTextView);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        imageView = findViewById(R.id.profile_image);
        saveBtn=findViewById(R.id.SaveBtn);
        bio=findViewById(R.id.BioEditText);

        if(user.getPhotoUrl()==null){

        }else {
            photoURL = user.getPhotoUrl().toString();
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(Firebaseauth.getCurrentUser()!=null){
            userID=Firebaseauth.getCurrentUser().getUid();
            DocumentReference docRef = db.collection("users").document(userID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            firstName.setText(document.get("firstName").toString());
                            lastName.setText(document.get("lastName").toString());
                            if(document.get("bio")==null){

                            }else {
                                bio.setText(document.get("bio").toString());
                            }
                            if(document.get("DOB")==null){

                            }else {
                                dateTV.setText(document.get("DOB").toString());
                            }
                        } else {
                        }
                    } else {
                    }
                }
            });
        }

        if(user.getPhotoUrl()!=null){
            Glide.with(getApplicationContext()).load(user.getPhotoUrl()).apply(new RequestOptions().fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL)).into(imageView);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userID = Firebaseauth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("firstName", firstName.getText().toString());
                user.put("lastName", lastName.getText().toString());
                user.put("bio",bio.getText().toString());
                user.put("DOB",dateTV.getText().toString());
                user.put("photoURL",photoURL);
                Intent intent = new Intent(getApplicationContext(), MainContent.class);
                startActivity(intent);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });
        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, android.R.style.Theme_Holo,
                        setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                String date=i2+"/"+i1+"/"+i;
                dateTV.setText(date);
            }
        };

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   if(intent.resolveActivity(getPackageManager())!=null){
                     startActivityForResult(intent,RequestCode);
                   }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageView.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(user.getPhotoUrl()).apply(new RequestOptions().fitCenter()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL)).into(zoomImageView);
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageView.setVisibility(View.GONE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode) {
            switch (resultCode){
                case RESULT_OK :
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    handleUpload(bitmap);
                    userID = Firebaseauth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("photoURL",photoURL);
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "onsuccess: user profile is created for" + userID);
                        }
                    });
            }
        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteStream);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid+".JPEG");
        storageReference.putBytes(byteStream.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadUrl(storageReference);

            }
        });

    }
    private void getDownloadUrl(StorageReference storageReference){
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                setUserProfileUrl(uri);
            }
        });
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        firebaseUser.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG);
            }
        });
    }
}