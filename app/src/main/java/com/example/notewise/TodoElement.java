package com.example.notewise;

public class TodoElement {

    private String Text;
    private boolean completed;



    public TodoElement(String text) {
        Text = text;
    }

    public TodoElement() {
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
