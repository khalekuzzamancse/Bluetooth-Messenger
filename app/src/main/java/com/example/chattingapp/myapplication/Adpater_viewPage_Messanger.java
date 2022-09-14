package com.example.chattingapp.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Adpater_viewPage_Messanger extends FragmentStateAdapter {


    public Adpater_viewPage_Messanger(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new Fragment_DeviceList();
       else  if(position==1)
           return  new Fragment_ChatList();
        return new  Fragment_DeviceList();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
