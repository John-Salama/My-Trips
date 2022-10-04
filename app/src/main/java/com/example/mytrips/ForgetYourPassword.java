package com.example.mytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class ForgetYourPassword extends AppCompatActivity {

    private TextView mEmailTextView;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_your_password);
        auth = FirebaseAuth.getInstance();
        mEmailTextView = findViewById(R.id.forgot_email_text);
        Button sendBtn = findViewById(R.id.forgot_password_btn);
        sendBtn.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String email = mEmailTextView.getText().toString().trim();
        if(email.isEmpty()){
            mEmailTextView.setError("email is not valid");
            mEmailTextView.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmailTextView.setError("email is not valid");
            mEmailTextView.requestFocus();
        }else{
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetYourPassword.this,"check your email",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgetYourPassword.this,"an error occurred",Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}