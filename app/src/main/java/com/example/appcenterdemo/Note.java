package com.example.appcenterdemo;

import java.util.UUID;

public class Note {
    String id;
    String text;

    public Note(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }
}
