package com.example.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<AdapterContact> contacts = new ArrayList<>();
    private OnContactClickListener listener;

    public ContactsAdapter(OnContactClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.contact_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setItems(List<AdapterContact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void bind(final AdapterContact contact) {
            ((TextView) view.findViewById(R.id.fullname_textview)).setText(contact.fullName);
            view.setOnClickListener(v -> listener.onClick(contact.id));
        }
    }
}
