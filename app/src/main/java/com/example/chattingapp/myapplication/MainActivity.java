package com.example.chattingapp.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ENABLE = 1;
    Button enable;
    Button disable;
    BluetoothAdapter bluetoothAdapter;
    Intent btEnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enable = findViewById(R.id.Bluetooth_enable);
        disable = findViewById(R.id.Bluetooth_Disable);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        ///6e511d4f-6bdd-431e-b8a7-78ed8bf801a8


        //enabling the bluetooth
//        enable.setOnClickListener(view -> {
//            EnableBluetooth();
//        });
//        //disabling the bluetooth
//        disable.setOnClickListener(view -> {
//          DisableBlueTooth();
//        });
Intent intent=new Intent(this,MainActivity2.class);
startActivity(intent);


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




