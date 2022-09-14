package com.example.chattingapp.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ENABLE = 1;
    Button enable;
    Button disable;
    BluetoothAdapter bluetoothAdapter;
    Intent btEnable;
    TabLayout tableLayout;
    ViewPager2 viewPager;
    Adpater_viewPage_Messanger adpater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.MainActivity_toolbar);
//        setSupportActionBar(toolbar);
//        try {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("");
//        } catch (NullPointerException exception) {
//            //
//        }
        tableLayout = findViewById(R.id.TabLayout);
        viewPager = findViewById(R.id.ViewPager);
        adpater = new Adpater_viewPage_Messanger(this);
        viewPager.setAdapter(adpater);
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tableLayout.getSelectedTabPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tableLayout.getTabAt(position).select();
                super.onPageSelected(position);
            }
        });


//        enable = findViewById(R.id.Bluetooth_enable);
//        disable = findViewById(R.id.Bluetooth_Disable);
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        btEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        ///6e511d4f-6bdd-431e-b8a7-78ed8bf801a8


//        enable.setOnClickListener(view -> {
//            LoadFragment(new Fragment_DeviceList());
//
//           // EnableBluetooth();
//        });
//        //disabling the bluetooth
//        disable.setOnClickListener(view -> {
//         // DisableBlueTooth();
//        });
//Intent intent=new Intent(this,MainActivity2.class);
//startActivity(intent);


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_ENABLE) {
//            if ((requestCode == RESULT_OK)) {
//                ;
//            }
//            else if(requestCode==RESULT_CANCELED)
//            {
//                ;
//            }
//        }
//    }

//    private void EnableBluetooth()
//    {
//        if (bluetoothAdapter != null) {
//            if (!bluetoothAdapter.isEnabled()) {
//
//                startActivityForResult(btEnable, REQUEST_ENABLE);
//            }
//        }
//    }
//    private void DisableBlueTooth()
//    {
//        if (bluetoothAdapter.isEnabled())
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                bluetoothAdapter.disable();
//            }


}




