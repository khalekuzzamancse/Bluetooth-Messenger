package com.example.chattingapp.myapplication.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chattingapp.myapplication.Entity_Message;
import com.example.chattingapp.myapplication.R;
import com.example.chattingapp.myapplication.UserDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;
    private static final String APP_NAME = "BTChat";
    private static final UUID MY_UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66");
    public static BluetoothDevice[] btArray;
    public static int Cliked;
    public  static  String DeviceName = "";
    Button listen, listDevices;
    ListView listView;
    TextView msg_box, status;
    EditText writeMsg;

    ListView mConversationView;
    ImageButton send;
    TextView UserName;
    BluetoothAdapter bluetoothAdapter;
    SendReceive sendReceive;
    int REQUEST_ENABLE_BLUETOOTH = 1;
    private ArrayAdapter<String> mConversationArrayAdapter;
    UserDatabase db;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case STATE_LISTENING:
                    status.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    status.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    status.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    status.setText("Connection Failed");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff, 0, msg.arg1);
                    // msg_box.setText(tempMsg);
                    mConversationArrayAdapter.add(DeviceName + ": " + tempMsg);
                    addToDatabase(DeviceName,"Me",tempMsg);
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        } catch (NullPointerException exception) {
            //
        }
        mConversationView = findViewById(R.id.ListView_conversation);
        UserName = findViewById(R.id.ChatActivity_UserNmae);

        mConversationArrayAdapter = new ArrayAdapter<>(this, R.layout.layout_single_message);
        mConversationView.setAdapter(mConversationArrayAdapter);
        db = UserDatabase.getInstance(this);

//insha-allah
        findViewByIdes();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
        }



        implementListeners();

        ClientClass clientClass = new ClientClass(btArray[Cliked]);
        clientClass.start();
        if (DeviceName != null) {
        //    DeviceName += btArray[Cliked].getName();
            UserName.setText(DeviceName);

        }
        status.setText("Connecting");
        getOldChat();


    }



    private void implementListeners() {

//        listDevices.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Set<BluetoothDevice> bt=bluetoothAdapter.getBondedDevices();
//                String[] strings=new String[bt.size()];
//                btArray=new BluetoothDevice[bt.size()];
//                int index=0;
//
//                if( bt.size()>0)
//                {
//                    for(BluetoothDevice device : bt)
//                    {
//                        btArray[index]= device;
//                        strings[index]=device.getName();
//                        index++;
//                    }
//                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,strings);
//                    listView.setAdapter(arrayAdapter);
//                }
//            }
//        });

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerClass serverClass = new ServerClass();
                serverClass.start();
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ClientClass clientClass=new ClientClass(btArray[i]);
//                clientClass.start();
//
//                status.setText("Connecting");
//            }
//        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = String.valueOf(writeMsg.getText());
                sendReceive.write(string.getBytes());
                mConversationArrayAdapter.add("Me:  " + string);
                writeMsg.setText("");
                addToDatabase("Me",DeviceName,string);
            }
        });
    }

    private void findViewByIdes() {
        // listen=(Button) findViewById(R.id.listen);
        listen = (Button) findViewById(R.id.ChatActivity_Listen);
        send = findViewById(R.id.send);
        //  listView=(ListView) findViewById(R.id.listview);
        //  msg_box =(TextView) findViewById(R.id.msg);
        // status=(TextView) findViewById(R.id.status);
        status = (TextView) findViewById(R.id.ChatActivity_Status);
        writeMsg = (EditText) findViewById(R.id.writemsg);
        // listDevices=(Button) findViewById(R.id.listDevices);
        //  listDevices=(Button) findViewById(R.id.ChatActivity_PairedDevice);
    }

    private class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass() {
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
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);

                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }

                if (socket != null) {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);

                    sendReceive = new SendReceive(socket);
                    sendReceive.start();

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
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                socket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);

                sendReceive = new SendReceive(socket);
                sendReceive.start();

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }
    }

    private class SendReceive extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = tempIn;
            outputStream = tempOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private  void addToDatabase(String sender,String receiver,String msg)
    {
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String time = s.format(new Date());
//            //
        Entity_Message message1 = new Entity_Message(sender, receiver, msg,time);
        db.MessageDAO().insertMessage(message1);
    }
    private void getOldChat() {
        ArrayList<Entity_Message> L = (ArrayList<Entity_Message>) db.MessageDAO().getAllMessage();
        for(int i=0;i<L.size();i++)
        {
            Log.i("DeviceName","entered"+L.size());
            if(L.get(i).sender.equals(DeviceName)||L.get(i).receiver.equals(DeviceName))
            {
                String msg=L.get(i).message;
                if(L.get(i).sender.equals("Me"))
                    mConversationArrayAdapter.add("Me: "+msg);
                else
                    mConversationArrayAdapter.add(DeviceName+":"+msg);
                Log.i("DeviceName",msg);
            }
        }

    }
}