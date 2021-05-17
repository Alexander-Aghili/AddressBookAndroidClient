package com.comp232.addressbookandroidclient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AddContactService extends Service {
    private final IBinder mBinder = new AddContactServiceBinder();
    Contact contact;

    public AddContactService(Contact contact) {
        this.contact = contact;
    }

    public class AddContactServiceBinder extends Binder {
        AddContactService getService() {
            return AddContactService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void addContact() {
        Client client = Client.create();

        //temp link, change later with AWS
        WebResource webResource = client.resource(Domain.getDomain() + "/rest/addContact");

        String input = GetJSONFromContact.getJSONFromContact(contact);

        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, input);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

    }

}
