package com.adedom.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyHolder> {

    private ArrayList<Data> items;

    public Adapter() {
    }

    Adapter(ArrayList<Data> items) {
        this.items = new ArrayList<>();
        this.items = items;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private final TextView textView1;
        private final TextView textView2;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(android.R.id.text1);
            textView2 = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(android.R.layout.simple_list_item_2, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        holder.textView1.setText(items.get(i).getTextView1());
        holder.textView2.setText(items.get(i).getTextView2());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refresh(ArrayList<Data> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
