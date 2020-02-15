package com.example.textapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Message> messageList = new ArrayList<Message>();
    private ListView lvMessages;
    private MessageLayoutAdapter adapter;
    private static final String[] MSG_PROJECTION_FILTER = {"DISTINCT " + Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.TYPE, Telephony.Sms.READ};
    private static final String MSG_SELECTION_FILTER = "address IS NOT NULL) GROUP BY (address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tbMessages));

        lvMessages = findViewById(R.id.lvMessages);
        adapter = new MessageLayoutAdapter(this, R.layout.layout_message, messageList);
        lvMessages.setAdapter(adapter);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            getPermissionToReadSMS();
//        } else {
            loadMessages();
//        }

        lvMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Message m = (Message) lvMessages.getItemAtPosition(position);
                Log.d("MSG", m.getContactName());
                Intent i = new Intent(MainActivity.this, ChatActivity.class);
                Bundle b = new Bundle();
                b.putString("contactName", m.getContactName());
                i.putExtras(b);
                startActivity(i);
            }
        });

    }

    private void loadMessages(){
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(
                Uri.parse("content://sms/inbox"),
                MSG_PROJECTION_FILTER,
                MSG_SELECTION_FILTER,
                null,
                null);
        int indexBody = smsInboxCursor.getColumnIndex(Telephony.Sms.BODY);
        int indexAddress = smsInboxCursor.getColumnIndex(Telephony.Sms.ADDRESS);
        int indexType = smsInboxCursor.getColumnIndex(Telephony.Sms.TYPE);
        int indexRead = smsInboxCursor.getColumnIndex(Telephony.Sms.READ);
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        adapter.clear();
        do {
            String address = smsInboxCursor.getString(indexAddress);
            Log.d("MSG", address);
            Message m = new Message(this, address, smsInboxCursor.getString(indexBody), smsInboxCursor.getInt(indexType), smsInboxCursor.getInt(indexRead));
            adapter.add(m);
        } while (smsInboxCursor.moveToNext());
    }

}
