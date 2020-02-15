package com.example.textapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class Message {
//    private String address;
    private Contact contact;
    private String message;
    private int type;
    private boolean read;
    private static final int DEFAULT_MSG_PREVIEW_SIZE = 60;

    public Message(Context context, String address, String message, int type, int read){
        this.contact = new Contact(context, address);
        this.type = type;
        if (read != 0){
            this.read = true;
        }

        this.message = message;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getMessagePreview(){
        if (message.indexOf('\n') > -1){
            return message.substring(0, message.indexOf('\n'));
        } else if (message.length() > DEFAULT_MSG_PREVIEW_SIZE){
            return message.substring(0, DEFAULT_MSG_PREVIEW_SIZE-3) + "...";
        }
        return message;
    }

    public String getContactName()
    {
        return this.contact.getContactName();
    }

}
