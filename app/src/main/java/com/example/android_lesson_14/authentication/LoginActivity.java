package com.example.android_lesson_14.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_lesson_14.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.android_lesson_14.R.layout.activity_login);
        email = findViewById(R.id.emailLog);
        password = findViewById(R.id.passwordLog);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view) {
        if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }else {
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            String noteId = "com.example.android_lesson_14.authentication";
                            NotificationCompat.Builder builder =
                                    new NotificationCompat.Builder(LoginActivity.this, noteId)
                                            .setAutoCancel(true)
                                            .setDefaults(Notification.DEFAULT_ALL)
                                            .setSmallIcon(R.drawable.ic_baseline_delete_outline_24)
                                            .setContentInfo("info")
                                            .setWhen(System.currentTimeMillis())
                                            .setContentTitle("Title")
                                            .setContentText("You login in account  "+firebaseAuth.getCurrentUser().getDisplayName())
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                            {
                                String channelId = "Your_channel_id";
                                NotificationChannel channel = new NotificationChannel(
                                        channelId,
                                        "Channel human readable title",
                                        NotificationManager.IMPORTANCE_HIGH);
                                notificationManager.createNotificationChannel(channel);
                                builder.setChannelId(channelId);
                            }
                            notificationManager.notify(1, builder.build());


                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Email is invalid verified", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void inClickForget(View view) {
        startActivity(new Intent(LoginActivity.this, ForGetPasswordActivity.class));
    }
}