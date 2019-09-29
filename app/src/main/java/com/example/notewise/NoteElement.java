package com.example.notewise;

public class NoteElement {

    private String Text;

    public NoteElement(String text) {
        Text = text;
    }

    public NoteElement() {
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

}
