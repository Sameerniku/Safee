package com.example.safteydevice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedContactsActivity extends AppCompatActivity implements contactAdapter.OnContactActionListener {
    private RecyclerView recyclerView;
    private contactAdapter adapter;
    private List<Contact> contacts;
    private TextView noContactsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_contacts);

        recyclerView = findViewById(R.id.recyclerView); // Ensure RecyclerView ID matches in XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load contacts from SharedPreferences
        contacts = loadContactsFromPreferences();
        adapter = new contactAdapter(contacts, this);
        recyclerView.setAdapter(adapter);

        // Initialize "No Contacts" TextView
        noContactsTextView = findViewById(R.id.noContactsTextView);
        updateNoContactsMessage();
    }

    @Override
    public void onEdit(Contact contact, int position) {
        // Handle the edit action here (e.g., open a dialog to edit the contact)
        // After editing, call adapter.notifyItemChanged(position); to update RecyclerView
    }

    @Override
    public void onDelete(int position) {
        // Remove the contact from the list
        contacts.remove(position);
        // Save the updated list to SharedPreferences
        saveContactsToPreferences();
        // Notify the adapter that the item was removed
        adapter.notifyItemRemoved(position);
        // Update the "No Contacts" message visibility
        updateNoContactsMessage();
    }

    private List<Contact> loadContactsFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("contacts", null);
        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
        List<Contact> loadedContacts = gson.fromJson(json, type);
        return loadedContacts == null ? new ArrayList<>() : loadedContacts;
    }

    private void saveContactsToPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        editor.putString("contacts", json);
        editor.apply();
    }

    private void updateNoContactsMessage() {
        if (contacts.isEmpty()) {
            noContactsTextView.setVisibility(View.VISIBLE);
        } else {
            noContactsTextView.setVisibility(View.GONE);
        }
    }
}
