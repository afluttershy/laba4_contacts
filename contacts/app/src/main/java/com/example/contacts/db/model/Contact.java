package com.example.contacts.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.contacts.db.ContactDao;

@Entity(tableName = ContactDao.TABLE_NAME)
public class Contact {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "surname")
    public String surname;
    @ColumnInfo(name = "patronymic")
    public String patronymic;
    @ColumnInfo(name = "phone")
    public String phone;
    @ColumnInfo(name = "email")
    public String email;

    public Contact(String name, String surname, String patronymic, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
        this.email = email;
    }
}
