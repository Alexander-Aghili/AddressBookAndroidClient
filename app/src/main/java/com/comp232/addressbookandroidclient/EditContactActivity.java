package com.comp232.addressbookandroidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditContactActivity extends AppCompatActivity {
    Contact contact;
    Button editAccountButton, backButton;
    EditText firstNameInput, lastNameInput, phoneNumberInput, addressInput;
    Thread editContactThread;
    EditContactService editService;
    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

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

        Bundle extras = getIntent().getExtras();
        contact = (Contact) extras.getSerializable("Contact");

        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        lastNameInput = (EditText) findViewById(R.id.lastNameInput);
        phoneNumberInput = (EditText) findViewById(R.id.phoneNumberInput);
        addressInput = (EditText) findViewById(R.id.addressInput);

        firstNameInput.setText(contact.getFirstName());
        lastNameInput.setText(contact.getLastName());
        phoneNumberInput.setText(contact.getPhoneNumber());
        addressInput.setText(contact.getAddress());

        editAccountButton = (Button) findViewById(R.id.editContactButton);
        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainScreen();
            }
        });

        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameInput.getText().toString();
                String lastName = lastNameInput.getText().toString();
                String phoneNumber = phoneNumberInput.getText().toString();
                String address = addressInput.getText().toString();
                Contact newContact = new Contact(firstName, lastName, phoneNumber, address, contact.getID());

                InputValidation validator = new InputValidation(firstNameInput, lastNameInput, phoneNumberInput, addressInput);
                if (!validator.checkValidInputs()) return;

                editService = new EditContactService(newContact);
                String contactValidation = validator.isUniqueInfo(contacts, newContact);

                if(contact.equals(newContact) || contactValidation.equals("same info")) {
                    backToMainScreen();
                    return;
                }

                if (!firstName.equals(contact.getFirstName())) {
                    editContactThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            editService.editFirstName();
                        }
                    });
                    startThread();
                }
                if (!lastName.equals(contact.getLastName())) {
                    editContactThread = new Thread(new Runnable() {
                         @Override
                         public void run() {
                             editService.editLastName();
                         }
                    });
                    startThread();
                }
                if (!phoneNumber.equals(contact.getPhoneNumber())) {
                    editContactThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            editService.editPhoneNumber();
                        }
                    });
                    startThread();
                }
                if (!address.equals(contact.getAddress())) {
                    editContactThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            editService.editAddress();
                        }
                    });
                    startThread();
                }
                backToMainScreen();
            }
        });
    }

    private void startThread() {
        try {
            editContactThread.start();
            editContactThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backToMainScreen() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}