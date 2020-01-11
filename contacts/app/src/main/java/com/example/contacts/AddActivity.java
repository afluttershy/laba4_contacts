package com.example.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contacts.db.ContactDao;
import com.example.contacts.db.ContactsDatabase;
import com.example.contacts.db.model.Contact;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AddActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button confirmBtn = findViewById(R.id.confirm);
        confirmBtn.setOnClickListener(v -> saveContact());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void saveContact() {
        String surname = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String name = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String otchestvo = ((EditText) findViewById(R.id.editText4)).getText().toString();
        String tel = ((EditText) findViewById(R.id.editText5)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText6)).getText().toString();

        if (surname.equals("") || name.equals("") || otchestvo.equals("") || tel.equals("") || email.equals("")) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show();
            return;
        }

        ContactsDatabase db = ContactsDatabase.getInstance(this);
        ContactDao dao = db.conactsDao();

        compositeDisposable.add(dao.insert(new Contact(null, name, surname, otchestvo, tel, email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish)
        );
    }
}
