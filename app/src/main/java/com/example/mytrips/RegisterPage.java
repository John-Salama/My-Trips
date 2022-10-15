package com.example.mytrips;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Objects;

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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final StorageReference mStorageReference =  FirebaseStorage.getInstance().getReference();

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

    private boolean uploadImage(String imageName) {
        final boolean[] uploadStatue = new boolean[1];
        uploadStatue[0] = true;
        StorageReference imagesRef = mStorageReference.child("images/" + imageName);
        imagesRef.putFile(mSelectedImageUri).addOnSuccessListener(taskSnapshot -> uploadStatue[0] = true).addOnFailureListener(e -> uploadStatue[0] = false);
        return uploadStatue[0];
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
            }else if(mPassword.length()<8){
                mPasswordView.setError("password is short");
                mPasswordView.requestFocus();
            }
            else{
                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                //suploadImage(mEmail);
                                Toast.makeText(RegisterPage.this, "user registered successfully", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
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

    private Uri mSelectedImageUri;
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
                        mSelectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    mSelectedImageUri);
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