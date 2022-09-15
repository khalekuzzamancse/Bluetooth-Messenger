package com.example.chattingapp.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.chattingapp.myapplication.Entity_Message;
import com.example.chattingapp.myapplication.R;
import com.example.chattingapp.myapplication.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UserDatabase db;
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
        Button b = view.findViewById(R.id.button);
        Button done = view.findViewById(R.id.done);
        EditText sender = view.findViewById(R.id.from);
        EditText receiver = view.findViewById(R.id.to);
        EditText message = view.findViewById(R.id.message);
        db = UserDatabase.getInstance(getActivity());

        b.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MainActivity2.class);
            startActivity(intent);

        });
        done.setOnClickListener(view1 -> {
                show();

//            Entity_Message message1 = new Entity_Message("khalek", "A.Noor", "Bismillah",time);
//            db.MessageDAO().insertMessage(message1);

        });


//insha-allah

        return view;
    }
    private void show()
    {
        ArrayList<Entity_Message> L = (ArrayList<Entity_Message>) db.MessageDAO().getAllMessage();
        for (int i = 0; i < L.size(); i++)
            Log.i("MessageList", L.get(i).sender + "->" + L.get(i).receiver + "->" + L.get(i).message + "->" + L.get(i).timeStamp);
    }

}