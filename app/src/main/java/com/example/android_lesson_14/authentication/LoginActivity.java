package com.example.android_lesson_14.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.android_lesson_14.R.layout.activity_login);
    }

    public void signIn(View view) {

    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}