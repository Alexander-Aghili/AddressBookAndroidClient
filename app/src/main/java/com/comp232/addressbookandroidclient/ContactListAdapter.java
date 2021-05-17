package com.comp232.addressbookandroidclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> contacts;
    Activity mContext;

    public ContactListAdapter(Activity context, ArrayList<Contact> contacts) {
        super(context, R.layout.contact_item_display, contacts);
        this.contacts = contacts;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= mContext.getLayoutInflater();
        View view = inflater.inflate(R.layout.contact_item_display, null,true);

        TextView firstNameView = (TextView) view.findViewById(R.id.firstName);
        TextView lastNameView = (TextView) view.findViewById(R.id.lastName);
        TextView phoneNumberView = (TextView) view.findViewById(R.id.phoneNumber);
        TextView addressView = (TextView) view.findViewById(R.id.address);
        Button editButton = (Button) view.findViewById(R.id.editButton);
        Button removeButton = (Button) view.findViewById(R.id.removeButton);

        final Contact contact = contacts.get(position);
        firstNameView.setText(contact.getFirstName());
        lastNameView.setText(contact.getLastName());
        phoneNumberView.setText(contact.getPhoneNumber());
        addressView.setText(contact.getAddress());

        //Button Performance
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread removeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RemoveContactService remove = new RemoveContactService(contact);
                        remove.removeContact();
                    }
                });

                //Replace with better solution later
                try {
                    removeThread.start();
                    removeThread.join();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                mContext.recreate();

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext.getApplicationContext(), EditContactActivity.class);
                i.putExtra("Contact", contact);
                mContext.startActivity(i);
            }
        });

        return view;
    }


}
