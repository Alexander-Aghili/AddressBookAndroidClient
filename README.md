# Address Book Android Client

Tested Devices:
AVD Android Emulator: Pixel 3a API 29 Emulator


If you continue to struggle, here are images on how it operates:

1. First time startup. There is an area in the middle for the contacts when you add them. The cloud icon in the bottom left is a refresh button. 
Once pressed, it will check for any edits or new contacts and display them.

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/startup.png?raw=true)

2. After clicking on Add Contact, you are presented to this page. Press back to go back without saving any changes.

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/AddContactStartup.png?raw=true)

3. If you attempt to enter an empty value, an outline of red will surround the textfield:

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/AddContactNoInfo.png?raw=true)

4. Completely fill out the fields:

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/AddContactFilled.png?raw=true)

5. Once the Add Contact button is pressed, you will be taken back to this page and displayed the new info:

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/NewContactAdded.png?raw=true)

6. Click the Edit Contact button for the Contact you want to edit to be presented with this page. If you attempt to enter an empty value, an outline of red will surround the textfield:

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/EditContactNoInfo.png?raw=true)

7. Completely edit the program: 

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/EditContactFilled.png?raw=true)

8. Once the Edit Contact button is pressed, the info will be altered and presented:

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/ContactEdited.png?raw=true)

9. Click the Remove Contact button on the Contact you want to get rid of to remove it:

![alt text](https://github.com/Alexander-Aghili/AddressBookAndroidClient/blob/master/Images/startup.png?raw=true)

Validation: User validation consists on ensuring that all fields have text. It also checks that the length of the phone number is resonable. 
When Adding the Contact, it checks that the ID created is unique. It checks that all the information is unique, if it is not, it just returns to the main screen.
