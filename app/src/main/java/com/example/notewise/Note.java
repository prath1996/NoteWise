package com.example.notewise;

import java.util.ArrayList;
import java.util.List;

public class Note extends File {

    public Note(String name) {
        super(name);
    }

    private List<NoteElement> ListOfNoteElement = new ArrayList<NoteElement>();

    public List getListOfNoteElement() {
        return ListOfNoteElement;
    }

    public void addNoteElement(NoteElement Element) {
        this.ListOfNoteElement.add(Element);
    }

    @Override
    int getType() {
        return 1;
    }
}
