package com.example.notewise;

import java.util.ArrayList;
import java.util.List;

public class NoteFile extends File {

    public NoteFile(String name) {
        super(name);
    }

    @Override
    int getType() {
        return 0;
    }
}
