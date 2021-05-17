package com.comp232.addressbookandroidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    Button addAccountButton, backButton;
    EditText firstNameInput, lastNameInput, phoneNumberInput, addressInput;
    AddContactService service;

    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        final GetContactsService getContactsService = new GetContactsService();
        Thread getContactsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                contacts = getContactsService.getContactsFromDatabase();
            }
        });

        try {
            getContactsThread.start();
            getContactsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        lastNameInput = (EditText) findViewById(R.id.lastNameInput);
        phoneNumberInput = (EditText) findViewById(R.id.phoneNumberInput);
        addressInput = (EditText) findViewById(R.id.addressInput);

        addAccountButton = (Button) findViewById(R.id.addContactButton);
        backButton = (Button) findViewById(R.id.backButton);

        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputValidation validator = new InputValidation(firstNameInput, lastNameInput, phoneNumberInput, addressInput);
                if  (!validator.checkValidInputs()) {
                    return;
                }
                Contact contact = new Contact(
                        firstNameInput.getText().toString(),
                        lastNameInput.getText().toString(),
                        phoneNumberInput.getText().toString(),
                        addressInput.getText().toString());
                String contactValidation = validator.isUniqueInfo(contacts, contact);

                if (contactValidation.equals("dupID")) return;

                if (contactValidation.equals("same info")) {
                    goToMainPage();
                    return;
                }

                service = new AddContactService(contact);
                service.onBind(getIntent());
                Thread addContactThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        service.addContact();
                    }
                });

                try {
                    addContactThread.start();
                    addContactThread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                goToMainPage();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainPage();
            }
        });
    }

    private void goToMainPage() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}