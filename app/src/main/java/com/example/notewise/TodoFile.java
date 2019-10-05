package com.example.notewise;

import java.util.ArrayList;
import java.util.List;

public class TodoFile extends File {

    public TodoFile(String name) {
        super(name);
    }

    @Override
    int getType() {
        return 1;
    }

}
