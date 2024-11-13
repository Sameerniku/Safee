


/*package com.example.safteydevice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ContactViewHolder> {
    private final List<Contact> contacts;
    private final OnContactActionListener actionListener;

    public interface OnContactActionListener {
        void onEdit(Contact contact, int position);
        void onDelete(int position);
    }

    public contactAdapter(List<Contact> contacts, OnContactActionListener actionListener) {
        this.contacts = contacts;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhone());

        holder.editButton.setOnClickListener(v -> actionListener.onEdit(contact, position));
        holder.deleteButton.setOnClickListener(v -> {
            contacts.remove(position);
            notifyItemRemoved(position); // Notify that an item was removed
            actionListener.onDelete(position);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView;
        Button editButton, deleteButton;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
*/





package com.example.safteydevice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ContactViewHolder> {
    private final List<Contact> contacts;
    private final OnContactActionListener actionListener;

    public interface OnContactActionListener {
        void onEdit(Contact contact, int position);
        void onDelete(int position);
    }

    public contactAdapter(List<Contact> contacts, OnContactActionListener actionListener) {
        this.contacts = contacts;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhone());

        holder.editButton.setOnClickListener(v -> actionListener.onEdit(contact, holder.getAdapterPosition()));
        holder.deleteButton.setOnClickListener(v -> actionListener.onDelete(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView;
        Button editButton, deleteButton;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

