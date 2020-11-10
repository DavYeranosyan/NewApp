package com.example.android_lesson_14;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseAdapter extends RecyclerView.ViewHolder {
    private TextView  title;
    private TextView decs;

    public BaseAdapter(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.textView);
        this.decs = itemView.findViewById(R.id.textView2);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDecs(String decs) {
        this.decs.setText(decs);
    }

}
