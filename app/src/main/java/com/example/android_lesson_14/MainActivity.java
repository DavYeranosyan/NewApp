package com.example.android_lesson_14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    EditText editText1, editText2;
    Button button;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.ed1);
        editText2 = findViewById(R.id.ed2);
        button = findViewById(R.id.but1);
        recyclerView = findViewById(R.id.rec1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = editText1.getText().toString();
                String str2 = editText2.getText().toString();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Book").push();
                Model model = new Model();
                model.setTitle(str1);
                model.setDesc(str2);
                databaseReference.setValue(model);

            }
        });




    }

}