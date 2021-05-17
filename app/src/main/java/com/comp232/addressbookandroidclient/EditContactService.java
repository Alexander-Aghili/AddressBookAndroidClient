package com.comp232.addressbookandroidclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class EditContactService {

    String input;
    private final String domain = Domain.getDomain() + "/rest/editContact";

    public EditContactService(Contact contact) {
        input = GetJSONFromContact.getJSONFromContact(contact);
    }

    public void editFirstName() {
        Client client = Client.create();
        WebResource webResource = client.resource(domain + "/firstname");
        makePostRequest(webResource);
    }

    public void editLastName() {
        Client client = Client.create();
        WebResource webResource = client.resource(domain + "/lastname");
        makePostRequest(webResource);
    }

    public void editPhoneNumber() {
        Client client = Client.create();
        WebResource webResource = client.resource(domain + "/phonenumber");
        makePostRequest(webResource);
    }

    public void editAddress() {
        Client client = Client.create();
        WebResource webResource = client.resource(domain + "/address");
        makePostRequest(webResource);
    }

    private void makePostRequest(WebResource webResource) {
        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, input);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

    }

}
