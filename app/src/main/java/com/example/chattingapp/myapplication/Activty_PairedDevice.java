package com.example.chattingapp.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Activty_PairedDevice extends AppCompatActivity {
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_FAILED = 4;
    static final int STATE_MESSAGE_RECIVED = 5;
    private static final String APP_NAME = "BTChat";
    private static final UUID MY_UUID = UUID.fromString("6e511d4f-6bdd-431e-b8a7-78ed8bf801a8");
    Button view;
    Button listen;
    Button send;
    EditText writeMsg;
    TextView msG;
    ListView pairedDeviceList;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;
    int REQUEST_ENABLE_BT = 1;
    SendRecivedMessage sendRecivedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_paired_device);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //


        listen = findViewById(R.id.listen);
        send = findViewById(R.id.send);
        msG = findViewById(R.id.message_show);
        writeMsg = findViewById(R.id.writeMessage);
        view = findViewById(R.id.view);
        pairedDeviceList = findViewById(R.id.PairedDeviceList);
        implementAllListener();


    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == STATE_MESSAGE_RECIVED) {
                byte[] readBuffer= (byte[]) msg.obj;
                String tempMsg=new String(readBuffer,0,msg.arg1);
                msG.setText(tempMsg);

            }

            return true;
        }
    });

    private void implementAllListener() {
        view.setOnClickListener(view1 -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
                String[] str = new String[bt.size()];
                btArray=new BluetoothDevice[bt.size()];
                int index = 0;
                if (bt.size() > 0) {
                    for (BluetoothDevice device : bt) {
                        str[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> stringArrayAdapter
                            = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, str);
                    pairedDeviceList.setAdapter(stringArrayAdapter);
                }


            }


        });
        listen.setOnClickListener(view1 -> {
            ServerClass serverClass = new ServerClass();
            serverClass.start();
        });
        pairedDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClientClass clientClass=new ClientClass(btArray[i]);
                clientClass.start();
                Log.i("STATE","connecting");
            }
        });
        send.setOnClickListener(view1 -> {
            String s=writeMsg.getText().toString();
            sendRecivedMessage.write(s.getBytes());

        });

///insha-allah
    }

    private class ServerClass extends Thread {

        private BluetoothServerSocket serverSocket;

        public ServerClass() {
            if (ActivityCompat.checkSelfPermission(Activty_PairedDevice.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            }
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void run() {

            BluetoothSocket socket = null;
            while (socket == null) {
                try {
                    Log.i("STATE", "connecting");
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socket != null) {
                    Log.i("STATE", "connected");

                    sendRecivedMessage=new SendRecivedMessage(socket);
                    sendRecivedMessage.start();
                    break;
                }

            }
        }

    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;
            try {
                if (ActivityCompat.checkSelfPermission(Activty_PairedDevice.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                }
                socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            if (ActivityCompat.checkSelfPermission(Activty_PairedDevice.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            }
            try {
                socket.connect();
                Message message=Message.obtain();
                message.what=STATE_CONNECTED;
                handler.sendMessage(message);
                sendRecivedMessage=new SendRecivedMessage(socket);
                sendRecivedMessage.start();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private class SendRecivedMessage extends Thread{

        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private  final OutputStream outputStream;
        public SendRecivedMessage(BluetoothSocket socket)
        {
            bluetoothSocket=socket;
            InputStream tempIn=null;
            OutputStream tempOut=null;
            try {
                tempIn=bluetoothSocket.getInputStream();
                tempOut=bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream=tempIn;
            outputStream=tempOut;
        }
        public void run()
        {
            byte[] buffer=new byte[1024];
            int bytes; //bytes=numbers of bytes
            while (true)
            {
                try {
                    bytes= inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }






}