package com.example.chattingapp.myapplication.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chattingapp.myapplication.R;

import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_DeviceList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_DeviceList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView PairedDeviceList;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_DeviceList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_DeviceList.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_DeviceList newInstance(String param1, String param2) {
        Fragment_DeviceList fragment = new Fragment_DeviceList();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__device_list, container, false);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        PairedDeviceList = view.findViewById(R.id.PairedDeviceList);
        Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
        String[] strings = new String[bt.size()];
        btArray = new BluetoothDevice[bt.size()];
        int index = 0;

        if (bt.size() > 0) {
            for (BluetoothDevice device : bt) {
                btArray[index] = device;
                strings[index] = device.getName();
                index++;
            }
            MainActivity2.btArray=btArray;

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.layout_single_device_name, strings);
            PairedDeviceList.setAdapter(arrayAdapter);
     }
        PairedDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity2.DeviceName=btArray[i].getName();
                Intent intent=new Intent(getActivity(),MainActivity2.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showPairedDevice() {


    }

}