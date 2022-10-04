package com.example.mytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private TextView mRegistration;
    private TextView mForgotPassword;
    private TextView mEmailText;
    private TextView mPasswordText;
    private Button mLoginBtn;
    Intent addtrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRegistration = findViewById(R.id.register_text);
        mForgotPassword = findViewById(R.id.forgot_password_text);
        mEmailText = findViewById(R.id.login_email_text);
        mPasswordText = findViewById(R.id.login_password_text);
        mLoginBtn = findViewById(R.id.login_btn);
        goToRegistration();
        goToForgot();
        /*mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtrip = new Intent(MainActivity.this,AddTripPage.class);
                startActivity(addtrip);
            }
        });*/
         login();
    }

    private void login() {
        mLoginBtn.setOnClickListener(v -> {
            String Email = mEmailText.getText().toString().replace(".",",");
            String Password = mPasswordText.getText().toString();
            if(Email.equals("")||Password.equals("")){
                Toast.makeText(MainActivity.this,"please fill all the fields",Toast.LENGTH_LONG).show();
            }else{
                db.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(Email)){
                            String getPass = snapshot.child(Email).child("password").getValue(String.class);
                            assert getPass != null;
                            if(getPass.equals(Password)){
                                Toast.makeText(MainActivity.this,"login successful",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this,"wrong email",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }

    private void goToForgot() {
        mForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,ForgetYourPassword.class);
            startActivity(intent);
        });
    }

    private void goToRegistration() {
        mRegistration.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RegisterPage.class);
            startActivity(intent);
        });
    }
}