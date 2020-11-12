package com.example.android_lesson_14;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseAdapter extends RecyclerView.ViewHolder {
    private TextView  title;
    private TextView decs;
    public LinearLayout root;
    public ImageButton delete;
    public BaseAdapter(@NonNull View itemView) {
        super(itemView);
        this.delete = itemView.findViewById(R.id.deleteBtn);
        this.root = itemView.findViewById(R.id.liners);
        this.title = itemView.findViewById(R.id.text1);
        this.decs = itemView.findViewById(R.id.text2);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDecs(String decs) {
        this.decs.setText(decs);
    }

}
