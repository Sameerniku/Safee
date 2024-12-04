pCompatActivity {
    private List<Contact> contacts;
    private contactAdapter adapter;






package com.example.safteydevice;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;


import android.location.Location;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> contacts;
    private contactAdapter adapter;

    private LocationManager locationManager;
    private static final int LOCATION_PERMISSION_CODE = 101; // or any integer of your choice


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Initialize and set up RecyclerView for contacts
        contacts = loadContactsFromPreferences(); // Load saved contacts on startup
        RecyclerView recyclerView = findViewById(R.id.contactsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up adapter for RecyclerView
        adapter = new contactAdapter(contacts, new contactAdapter.OnContactActionListener() {
            @Override
            public void onEdit(Contact contact, int position) {
                openEditContactDialog(contact, position);
            }

            @Override
            public void onDelete(int position) {
                contacts.remove(position);
                saveContactsToPreferences(); // Save updated contacts list
                adapter.notifyItemRemoved(position); // Update RecyclerView
            }
        });
        recyclerView.setAdapter(adapter);
        updateContactsUI();

        // Set up buttons
        ImageButton sosButton = findViewById(R.id.imageButton);
        ImageButton addContactsButton = findViewById(R.id.imageButton4);
        ImageButton emergencyDialButton = findViewById(R.id.imageButton5);

        // Set up onClick listeners for each button
        sosButton.setOnClickListener(view -> getLocation()); // Fetch location when SOS is pressed
        addContactsButton.setOnClickListener(view -> showAddContactDialog());
        emergencyDialButton.setOnClickListener(view -> dialSavedContact());

        // Request location permissions if not already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }



    private void updateContactsUI() {
        TextView noContactsTextView = findViewById(R.id.noContactsTextView);
        if (contacts.isEmpty()) {
            noContactsTextView.setVisibility(View.VISIBLE);
        } else {
            noContactsTextView.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        EditText nameInput = dialogView.findViewById(R.id.editTextContactName);
        EditText numberInput = dialogView.findViewById(R.id.editTextPhoneNumber);

        builder.setView(dialogView);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = nameInput.getText().toString();
            String number = numberInput.getText().toString();
            saveContact(name, number);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void saveContact(String name, String number) {
        Contact contact = new Contact(name, number);
        contacts.add(contact);
        saveContactsToPreferences();
        adapter.notifyDataSetChanged();
    }

    private void saveContactsToPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        editor.putString("contacts", json);
        editor.apply();
    }



    private List<Contact> loadContactsFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("contacts", null);
        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
        contacts = gson.fromJson(json, type);

        if (contacts == null) {
            contacts = new ArrayList<>();
        }
        return null;
    }

    private void openEditContactDialog(Contact contact, int position) {
        showAddContactDialog();
    }



    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Call sendEmergencyMessage with location data
                    sendEmergencyMessage(latitude, longitude);

                    // Stop further location updates to save battery
                    locationManager.removeUpdates(this);
                }
            });
        }
    }

    private void sendEmergencyMessage(double latitude, double longitude) {
        // Define the emergency contact number
        String phoneNumber = "+919337651696"; // Replace with your emergency contact number
        // Format the message with the location coordinates
        String message = "Emergency! I need help. My location is: " +
                "Latitude: " + latitude + ", Longitude: " + longitude +
                ". Please send assistance.";

        try {
            // Use SmsManager to send the SMS
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Emergency message sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Display an error message if the SMS fails to send
            Toast.makeText(this, "Failed to send message: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }




    private void dialSavedContact() {
        SharedPreferences sharedPreferences = getSharedPreferences("Contacts", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PhoneNumber", "");

        if (!phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No Contact Found")
                    .setMessage("Please add an emergency contact first.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}




