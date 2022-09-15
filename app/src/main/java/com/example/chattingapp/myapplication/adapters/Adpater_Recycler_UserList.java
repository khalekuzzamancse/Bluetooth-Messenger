package com.example.chattingapp.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattingapp.myapplication.R;
import com.example.chattingapp.myapplication.datatypes.DataType_Recycler_UserList;
import com.example.chattingapp.myapplication.ui.MainActivity2;
import com.example.chattingapp.myapplication.viewholders.ViewHolder_recycler_UserList;

import java.util.List;

public class Adpater_Recycler_UserList extends RecyclerView.Adapter<ViewHolder_recycler_UserList> {
    Context context;
    List<DataType_Recycler_UserList> list;

    public Adpater_Recycler_UserList(Context context, List<DataType_Recycler_UserList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder_recycler_UserList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewOfRecyclerLayout = LayoutInflater.from(context).
                inflate(R.layout.layout_recycler_user_list, parent, false);

        ViewHolder_recycler_UserList viewHolder =
                new ViewHolder_recycler_UserList(viewOfRecyclerLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_recycler_UserList holder, int position) {
        String name = list.get(position).Username;
        holder.userName.setText(name);
        holder.userName.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity2.class);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
