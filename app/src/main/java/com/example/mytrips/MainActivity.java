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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView registration = findViewById(R.id.register_text);
        TextView forgotPassword = findViewById(R.id.forgot_password_text);
        TextView emailText = findViewById(R.id.login_email_text);
        TextView passwordText = findViewById(R.id.login_password_text);
        Button loginBtn = findViewById(R.id.login_btn);
        registration.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RegisterPage.class);
            startActivity(intent);
        });
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,ForgetYourPassword.class);
            startActivity(intent);
        });
        loginBtn.setOnClickListener(v -> {
            String Email = emailText.getText().toString().replace(".",",");
            String Password = passwordText.getText().toString();
            if(Email.equals("")||Password.equals("")){
                Toast.makeText(MainActivity.this,"please fill all the fields",Toast.LENGTH_LONG).show();
            }else{
                db.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(Email)){
                            final String getPass = snapshot.child(Email).child("password").getValue(String.class);
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
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}