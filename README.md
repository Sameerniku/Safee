Here’s a sample README file for your **Safety Device** Android project:

---

# Safety Device(Safee) - Emergency Contact App

## Overview
**Safety Device** is an Android application that allows users to quickly send emergency SMS messages with their current location and contact emergency services. The app enables users to:
- Add, edit, and delete emergency contacts.
- Send an SOS message with a location link to a pre-set contact.
- Directly call a saved emergency contact.

This project is designed to enhance user safety by providing quick access to critical contacts and enabling location sharing in emergencies.

## Features
- **SOS Button:** Sends an SMS with the user's current GPS location to a predefined emergency number.
- **Emergency Dial:** Instantly dials the user's saved emergency contact number.
- **Contact Management:** Add, edit, and delete emergency contacts stored locally on the device.

## Technology Stack
- **Java:** Core language used for Android development.
- **Android SDK:** For user interface components and native features.
- **Google Gson:** For JSON serialization and deserialization of the contact list.
- **Location Services & SMS Manager:** For obtaining GPS coordinates and sending SMS.

## Getting Started

### Prerequisites
- Android Studio installed on your computer.
- A physical Android device for testing (recommended) since SMS and GPS features may not work accurately on emulators.

### Permissions
Ensure the following permissions are added to `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.CALL_PHONE" />
```

### Setup
1. Clone this repository or download the source code.
2. Open the project in Android Studio.
3. Build and run the app on a connected Android device.

### Important: 
Test the SMS and call features carefully, as real SMS messages will be sent and calls will be dialed.

## Usage
1. **Add Emergency Contact:**
   - Tap the "Add Contact" button to open the contact dialog.
   - Enter the contact's name and phone number, then tap "Save."

2. **Sending an SOS Message:**
   - Tap the "SOS" button.
   - The app will request the user's current location and send an SMS with a Google Maps link to the saved contact.

3. **Emergency Dial:**
   - Tap the "Call" button to dial the saved emergency contact number.

## Code Structure
- **MainActivity.java:** Contains the core functionality for the SOS button, emergency dial, and contact management.
- **Contact.java:** Model class for contact data (name and phone number).
- **contactAdapter.java:** Adapter for managing contact data in the RecyclerView.

## Core Functions

- **sendEmergencyMessage():** Retrieves the user's current location and sends an SMS with a Google Maps link to the designated emergency contact.
- **dialSavedContact():** Calls the saved emergency contact using the default phone dialer.
- **saveContactsToPreferences():** Saves the contact list to SharedPreferences in JSON format.
- **loadContactsFromPreferences():** Loads the contact list from SharedPreferences.

## Acknowledgments
- Android development resources and documentation.
- Google Gson library for JSON management.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

---

