package com.example.android_lesson_14.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_lesson_14.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText name, surname, email, age, password;
    FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        age = findViewById(R.id.age);
        password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void signIn(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public void goMyNewAcc(View view) {
        if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Werryyy Gooood", Toast.LENGTH_LONG).show();
                        String nameF = name.getText().toString();
                        String surnameF = surname.getText().toString();
                        String emailF = email.getText().toString();
                        String ageF = age.getText().toString();
                        databaseReference  = FirebaseDatabase.getInstance().getReference("book").push();
                        Model model = new Model();
                        model.setId(databaseReference.getKey());
                        model.setName(nameF);
                        model.setSurname(surnameF);
                        model.setEmail(emailF);
                        model.setAge(Integer.parseInt(ageF));
                        databaseReference.setValue(model);
                    }
                }
            });
        }
    }
}