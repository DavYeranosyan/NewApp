package com.example.android_lesson_14;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editText1, editText2;
    Button button;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    DatabaseReference databaseReference;
    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        recyclerView = findViewById(R.id.recycleView);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        alertBuilder = new AlertDialog.Builder(this);
        button = findViewById(R.id.button);
        Picasso.get()
                .load("https://static.toiimg.com/photo/72975551.cms")
                .into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(editText1.getText());
                String description = String.valueOf(editText2.getText());
                databaseReference = FirebaseDatabase.getInstance().getReference("book").push();
                Model model = new Model();
                model.setId(databaseReference.getKey());
                model.setTitle(title);
                model.setDesc(description);
                databaseReference.setValue(model);
            }
        });
        show();
    }

    private void show() {
        final Query query = FirebaseDatabase.getInstance().getReference().child("book");
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, new SnapshotParser<Model>() {
                            @NonNull
                            @Override
                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Model(snapshot.child("id").getValue().toString(),
                                        snapshot.child("title").getValue().toString(),
                                        snapshot.child("desc").getValue().toString());
                            }
                        })
                        .build();
        adapter = new FirebaseRecyclerAdapter<Model, BaseAdapter>(options) {
            @Override
            public BaseAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.a1, parent, false);
                return new BaseAdapter(view);
            }

            @Override
            protected void onBindViewHolder(BaseAdapter holder, final int position, final Model model) {
                holder.setDecs(model.getDesc());
                holder.setTitle(model.getTitle());

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference().child("book").child(model.getId()).removeValue();
                    }
                });
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View view1 = getLayoutInflater().inflate(R.layout.alert, null);
                        final EditText editText1 = view1.findViewById(R.id.ed1);
                        final EditText editText2 = view1.findViewById(R.id.ed2);
                        editText1.setText(model.getTitle());
                        editText2.setText(model.getDesc());
                        alertBuilder.setView(view1).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("book").child(model.getId()).setValue(new Model(model.getId(), editText1.getText().toString(), editText2.getText().toString()));
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alertDialog = alertBuilder.create();
                        alertDialog.setTitle("Change My Date");
                        alertDialog.show();
                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
