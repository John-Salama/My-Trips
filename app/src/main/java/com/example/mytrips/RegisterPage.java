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
import android.util.Patterns;
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
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-trips-66039-default-rtdb.firebaseio.com/");
    private Button mSignUpBtn;
    private TextView mUserNameView;
    private TextView mEmailView;
    private TextView mPasswordView;
    private TextView mPasswordConfirmationView;
    private String mUser;
    private String mEmail;
    private String mPassword;
    private String mPasswordConfirm;
    private TextView mSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        initializeViewComponent();
        imageChooser();
        register();
    }

    private void initializeViewComponent() {
        mSignupMassage = findViewById(R.id.signup_img);
        mSelectImage = findViewById(R.id.select_photo);
        mSignUpBtn = findViewById(R.id.signup_btn);
        mUserNameView = findViewById(R.id.signup_user_name_text);
        mEmailView = findViewById(R.id.signup_email_text);
        mPasswordView = findViewById(R.id.signup_password_text);
        mPasswordConfirmationView = findViewById(R.id.signup_password_confirmation_text);
    }

    private void getRegistrationData() {
        mUser = mUserNameView.getText().toString().trim();
        mEmail = mEmailView.getText().toString().trim();
        mPassword = mPasswordView.getText().toString().trim();
        mPasswordConfirm = mPasswordConfirmationView.getText().toString().trim();
    }

    private void register() {
        mSignUpBtn.setOnClickListener(v -> {
            getRegistrationData();
            if(mUser.isEmpty()|| mEmail.isEmpty()|| mPassword.isEmpty()|| mPasswordConfirm.isEmpty())
                Toast.makeText(RegisterPage.this,"please fill all fields",Toast.LENGTH_LONG).show();
            else if(!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                mEmailView.setError("email is not valid");
                mEmailView.requestFocus();
            }
            else if(!mPassword.equals(mPasswordConfirm)){
                mPasswordConfirmationView.setError("password not matched");
                mPasswordConfirmationView.requestFocus();
            }
            else{
                db.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mEmail = mEmail.replace(".",",");
                        if(snapshot.hasChild(mEmail)){
                            mEmailView.setError("email already registered");
                            mEmailView.requestFocus();
                        }else{
                            db.child("users").child(mEmail).child("fullName").setValue(mUser);
                            db.child("users").child(mEmail).child("email").setValue(mEmail);
                            db.child("users").child(mEmail).child("password").setValue(mPassword);
                            Toast.makeText(RegisterPage.this,"user registered successfully",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    //prepare intent to open gallery
    private void imageChooser() {
        mSelectImage.setOnClickListener(v -> {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    });
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