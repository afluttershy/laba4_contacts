package com.example.contacts;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.contacts.db.ContactDao;
import com.example.contacts.db.ContactsDatabase;
import com.example.contacts.db.model.Contact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements OnContactClickListener {

    private ContactsAdapter adapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addBtn = findViewById(R.id.button);
        addBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
        });

        initAdapter();
        showContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initAdapter() {
        adapter = new ContactsAdapter(this);
        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void showContacts() {
        ContactsDatabase db = ContactsDatabase.getInstance(this);
        ContactDao dao = db.conactsDao();

        compositeDisposable.add(dao.observeAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(contacts -> {
                    List<AdapterContact> adapterContacts = new ArrayList<>();
                    for (Contact contact : contacts) {
                        adapterContacts.add(new AdapterContact(
                                contact.id,
                                contact.surname + " " + contact.name + " " + contact.patronymic
                        ));
                    }
                    return adapterContacts;
                })
                .subscribe(this::addContacts));
    }

    private void addContacts(List<AdapterContact> contacts) {
        adapter.setItems(contacts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(Long contactId) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra(ProfileActivity.CONTACT_ID, (long) contactId);
        startActivity(i);
    }
}
