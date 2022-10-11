package com.example.mytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-trips-66039-default-rtdb.firebaseio.com/");
    private TextView mRegistrationView;
    private TextView mForgotPasswordView;
    private TextView mEmailTextView;
    private TextView mPasswordTextView;
    private Button mLoginBtn;
    private String mEmail;
    private String mPassword;
    Intent addTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViewComponent();
        goToRegistration();
        goToForgot();
       /* mLoginBtn.setOnClickListener(view -> {
            addTrip = new Intent(MainActivity.this,AddNotePage.class);
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
                Toast.makeText(MainActivity.this,"please fill all the fields",Toast.LENGTH_LONG).show();
            }else{
                db.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(mEmail)){
                            String getPass = snapshot.child(mEmail).child("password").getValue(String.class);
                            assert getPass != null;
                            if(getPass.equals(mPassword)){
                                addTrip = new Intent(MainActivity.this,UpcomingPage.class);
                                startActivity(addTrip);
                            }else{
                                mPasswordTextView.setError("wrong password");
                                mPasswordTextView.requestFocus();
                            }
                        }
                        else{
                                mEmailTextView.setError("wrong email");
                                mEmailTextView.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }

    private void getLoginData() {
        mEmail = mEmailTextView.getText().toString().replace(".",",");
        mPassword = mPasswordTextView.getText().toString();
    }

    private void goToForgot() {
        mForgotPasswordView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,ForgetYourPassword.class);
            startActivity(intent);
        });
    }

    private void goToRegistration() {
        mRegistrationView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RegisterPage.class);
            startActivity(intent);
        });
    }
}