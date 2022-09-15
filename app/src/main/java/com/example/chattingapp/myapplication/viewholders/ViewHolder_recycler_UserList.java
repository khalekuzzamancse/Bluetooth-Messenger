package com.example.chattingapp.myapplication.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattingapp.myapplication.R;

public class ViewHolder_recycler_UserList extends RecyclerView.ViewHolder {
    public TextView userName;

    public ViewHolder_recycler_UserList(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.UserListRecycler_TextView_UserName);

    }
}
