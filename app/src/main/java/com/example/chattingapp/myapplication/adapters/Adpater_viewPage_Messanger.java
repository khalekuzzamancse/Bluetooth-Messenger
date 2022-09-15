package com.example.chattingapp.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chattingapp.myapplication.ui.Fragment_ChatList;
import com.example.chattingapp.myapplication.ui.Fragment_DeviceList;

public class Adpater_viewPage_Messanger extends FragmentStateAdapter {


    public Adpater_viewPage_Messanger(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==1)
            return new Fragment_DeviceList();
       else  if(position==0)
           return  new Fragment_ChatList();
        return new  Fragment_DeviceList();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
