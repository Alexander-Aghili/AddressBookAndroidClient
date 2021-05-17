package com.comp232.addressbookandroidclient;

import android.widget.EditText;

import java.util.ArrayList;

/*
Used to validate inputs from user input. Checks if user info exists, if all fields are filled in.
 */
public class InputValidation {
    EditText firstname, lastname, phonenumber, address;

    public InputValidation(EditText firstname, EditText lastname, EditText phonenumber, EditText address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    //Checks if text is "Invalid Input" or blank, returns and makes field say "Invalid Input" if true
    //Checks for each field, checks size for phone number.
    public boolean checkValidInputs() {
        String firstNameText = firstname.getText().toString();
        String lastNameText = lastname.getText().toString();
        String phoneNumberText = phonenumber.getText().toString();
        String addressText = address.getText().toString();
        String invalid = "Invalid Input";
        if (checkBlankInput(firstNameText) || firstNameText.equals(invalid)) {
            firstname.setText(invalid);
            return false;
        }

        if (checkBlankInput(lastNameText) || lastNameText.equals(invalid)) {
            lastname.setText(invalid);
            return false;
        }

        if (checkNotValidPhoneNumber(phoneNumberText) || phoneNumberText.equals(invalid)) {
            phonenumber.setText(invalid);
            return false;
        }

        if (checkBlankInput(addressText) || addressText.equals(invalid)) {
            address.setText(invalid);
            return false;
        }

        return true;
    }


    private boolean checkBlankInput(String input) {
        if (input.trim().equals(""))
            return true;
        return false;

    }

    private boolean checkNotValidPhoneNumber(String input) {
        int length = input.trim().length();
        if (length > 18 || length < 12)
            return true;
        return false;
    }

    //Reason that info is taken in during page entry is to avoid delay time when edit button pressed
    public String isUniqueInfo(ArrayList<Contact> contacts, Contact contact) {
        for (Contact contactFromArray: contacts) {
            if (contactFromArray.getID().equals(contact.getID()))
                return "dupID";

            if (contactFromArray.equals(contact))
                return "same info";
        }
        return "ok";
    }

}
