package com.comp232.addressbookandroidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

//Android application that connects to a database running the application.
//AWS hosting

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;
    GetContactsService con;

    ListView contactsList;
    Button addContactButtonMain, refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = new GetContactsService();
        Thread getContactsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                contacts = con.getContactsFromDatabase();
            }
        });

        try {
            getContactsThread.start();
            getContactsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.sort(contacts);

        ContactListAdapter listAdapter = new ContactListAdapter(this, contacts);
        contactsList = (ListView) findViewById(R.id.listOfContacts);
        contactsList.setAdapter(listAdapter);

        addContactButtonMain = (Button) findViewById(R.id.addContactButtonMain);
        addContactButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivity(i);
            }
        });

        refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
    }
}