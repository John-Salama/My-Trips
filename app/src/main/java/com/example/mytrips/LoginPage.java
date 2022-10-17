package com.example.mytrips;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginPage extends AppCompatActivity {
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-trips-66039-default-rtdb.firebaseio.com/");
    private TextView mRegistrationView;
    private TextView mForgotPasswordView;
    private TextView mEmailTextView;
    private TextView mPasswordTextView;
    private Button mLoginBtn;
    private String mEmail;
    private String mPassword;
    Intent addTrip;
    public static Bitmap mBitmap = null;
    public static final long MEGABYTES = 1024 * 1024;
    private final StorageReference mStorageReference =  FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViewComponent();
        goToRegistration();
        goToForgot();
        /*mLoginBtn.setOnClickListener(view -> {
            //addTrip = new Intent(LoginPage.this,AddNotePage.class);
            addTrip = new Intent(LoginPage.this, AddTripPage.class);
            startActivity(addTrip);
        });*/
         login();
    }

    private void initializeViewComponent() {
        mRegistrationView = findViewById(R.id.register_text);
        mForgotPasswordView = findViewById(R.id.forgot_password_text);
        mEmailTextView = findViewById(R.id.login_email_text);
        mPasswordTextView = findViewById(R.id.login_password_text);
        mLoginBtn = findViewById(R.id.login_btn);
    }

    private void login() {
        mLoginBtn.setOnClickListener(v -> {
            getLoginData();
            if(mEmail.equals("")|| mPassword.equals("")){
                Toast.makeText(LoginPage.this,"please fill all the fields",Toast.LENGTH_LONG).show();
            }else{
                mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                addTrip = new Intent(LoginPage.this,UpcomingPage.class);
                                startActivity(addTrip);
                            } else {
                                Toast.makeText(LoginPage.this, "Wrong password or email ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void getLoginData() {
        mEmail = mEmailTextView.getText().toString().trim();
        mPassword = mPasswordTextView.getText().toString().trim();
    }

    private void goToForgot() {
        mForgotPasswordView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this,ForgetYourPassword.class);
            startActivity(intent);
        });
    }

    private void goToRegistration() {
        mRegistrationView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this,RegisterPage.class);
            startActivity(intent);
        });
    }

    private void downloadImage(String fileName)
    {
        // Create a reference with an initial file path and name
        StorageReference imgRef =  mStorageReference.child("images/" + fileName);
        Task<byte[]> bytes = imgRef.getBytes(MEGABYTES);
        while(!bytes.isComplete());
        mBitmap = BitmapFactory.decodeByteArray(bytes.getResult(),0,bytes.getResult().length);
    }
}