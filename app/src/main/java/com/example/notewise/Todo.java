package com.example.notewise;

import java.util.ArrayList;
import java.util.List;

public class Todo extends File {

    private List<TodoElement> ListOfTodoElement = new ArrayList<TodoElement>();

    public List getListOfTodoElement() {
        return ListOfTodoElement;
    }

    public void addTodoElement(TodoElement Element) {
        this.ListOfTodoElement.add(Element);
    }

    public Todo(String name) {
        super(name);
    }

    @Override
    int getType() {
        return 0;
    }

}
