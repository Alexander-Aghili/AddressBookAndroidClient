package com.comp232.addressbookandroidclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetContactsService {

    public ArrayList<Contact> getContactsFromDatabase() {
        Client client = Client.create();

        WebResource webResource = client.resource(Domain.getDomain()+ "/rest/listContacts");

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String json = response.getEntity(String.class);
        try {
            return StringJSONToContacts(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Contact> StringJSONToContacts(String json) throws JSONException {
        ArrayList<Contact> contacts = new ArrayList<>();
        String remainingString = json.substring(json.indexOf("[") + 1, json.lastIndexOf("}"));
        if (remainingString.equals("]"))
            return contacts;
        while (true) {
            int lastEndJSON = remainingString.indexOf("}");

            if (!(lastEndJSON + 1 == remainingString.indexOf("]"))) {
                contacts.add(performContact(remainingString, lastEndJSON));
                remainingString = remainingString.substring(lastEndJSON + 2);
            } else {
                contacts.add(performContact(remainingString, lastEndJSON));
                return contacts;
            }
        }
    }

    private Contact performContact(String fullString, int endOfJSON) throws JSONException {
        String contactStringJSON = fullString.substring(0, endOfJSON + 1);
        JSONObject contactJSON = new JSONObject(contactStringJSON);
        return contactFromJSON(contactJSON);
    }


    private Contact contactFromJSON(JSONObject jsonObject) throws JSONException {
        return new Contact(
                jsonObject.getString("firstname"),
                jsonObject.getString("lastname"),
                jsonObject.getString("phonenumber"),
                jsonObject.getString("address"),
                jsonObject.getString("id"));
    }

}
