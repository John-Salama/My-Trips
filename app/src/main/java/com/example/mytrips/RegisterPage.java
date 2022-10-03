package com.example.mytrips;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class RegisterPage extends AppCompatActivity {
    private ImageView mSignupMassage;
    private Button mSignUpBtn;
    private TextView mSelectImage;
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-trips-66039-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        mSignupMassage = findViewById(R.id.signup_img);
        mSelectImage = findViewById(R.id.select_photo);
        mSignUpBtn = findViewById(R.id.signup_btn);
        TextView userName = findViewById(R.id.signup_user_name_text);
        TextView email = findViewById(R.id.signup_email_text);
        TextView password = findViewById(R.id.signup_password_text);
        TextView passwordConfirmation = findViewById(R.id.signup_password_confirmation_text);


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String Email = email.getText().toString().replace(".",",");
                String pass = password.getText().toString();
                String passConfirm = passwordConfirmation.getText().toString();
                if(user.isEmpty()||Email.isEmpty()||pass.isEmpty()||passConfirm.isEmpty())
                    Toast.makeText(RegisterPage.this,"please fill all fields",Toast.LENGTH_LONG).show();
                else if(!pass.equals(passConfirm))
                    Toast.makeText(RegisterPage.this,"password dose not matched",Toast.LENGTH_LONG).show();
                else{
                    db.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(Email))
                                Toast.makeText(RegisterPage.this,"email already registered",Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    db.child("users").child(Email).child("fullName").setValue(user);
                    db.child("users").child(Email).child("email").setValue(Email);
                    db.child("users").child(Email).child("password").setValue(pass);
                    Toast.makeText(RegisterPage.this,"user registered successfully",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    //prepare intent to open gallery
    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }

    //take photo from gallery
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap resized = Bitmap.createScaledBitmap(selectedImageBitmap,
                                (int) (mSignupMassage.getWidth()*0.8),
                                (int) (mSignupMassage.getHeight()*0.8), true);
                        mSignupMassage.setImageBitmap(resized);
                    }
                }
            });
}