package com.example.chattingapp.myapplication.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.chattingapp.myapplication.Entity_Message;
import com.example.chattingapp.myapplication.R;
import com.example.chattingapp.myapplication.UserDatabase;
import com.example.chattingapp.myapplication.adapters.Adpater_Recycler_UserList;
import com.example.chattingapp.myapplication.datatypes.DataType_Recycler_UserList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ChatList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ChatList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    UserDatabase db;
    List<DataType_Recycler_UserList> List;
    Adpater_Recycler_UserList adapter;
    RecyclerView r;
    HashMap<String, String> UserHashMap;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };
    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };

    public Fragment_ChatList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ChatList.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ChatList newInstance(String param1, String param2) {
        Fragment_ChatList fragment = new Fragment_ChatList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__chat_list, container, false);
       // Button b = view.findViewById(R.id.button);
        db = UserDatabase.getInstance(getActivity());
        checkPermissions();
        intializeDeviceList();

//        b.setOnClickListener(view1 -> {
//            Intent intent = new Intent(getActivity(), MainActivity2.class);
//            startActivity(intent);
//
//        });
        List = new ArrayList<DataType_Recycler_UserList>();
        adapter = new Adpater_Recycler_UserList(getActivity(), List);
        r = view.findViewById(R.id.ChatList_Recycler_UserList);
        r.setLayoutManager(new LinearLayoutManager(getActivity()));
        r.setAdapter(adapter);
        UserHashMap = new HashMap<String, String>();
        getUserList();

//insha-allah

        return view;
    }

    private void getUserList() {
        ArrayList<Entity_Message> L = (ArrayList<Entity_Message>) db.MessageDAO().getAllMessage();
        for (int i = 0; i < L.size(); i++) {
            String Sender = L.get(i).sender;
            String Recevier = L.get(i).receiver;
            if (!Sender.equals("Me")) {

                UserHashMap.put(Sender, "1");
            } else
                UserHashMap.put(Recevier, "1");

        }
        for (String user : UserHashMap.keySet()) {
            DataType_Recycler_UserList obj1 = new DataType_Recycler_UserList();
            obj1.Username = user;
            List.add(obj1);
        }
    }

    private void intializeDeviceList() {

       BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       // Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
        Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
        String[] strings = new String[bt.size()];
        BluetoothDevice[] btArray = new BluetoothDevice[bt.size()];
        int index = 0;

        if (bt.size() > 0) {
            for (BluetoothDevice device : bt) {
                btArray[index] = device;
                strings[index] = device.getName();
                index++;
                Log.i("DeviceAddress",device.getAddress());
            }
            MainActivity2.btArray = btArray;
        }
    }
    private void checkPermissions(){
        int permission1 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_SCAN);
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    1
            );
        } else if (permission2 != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_LOCATION,
                    1
            );
        }
    }


}