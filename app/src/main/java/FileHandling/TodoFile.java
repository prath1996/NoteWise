package FileHandling;

class TodoFile extends File {

    TodoFile(String name) {
        super(name);
    }

    @Override
    int getType() {
        return 1;
    }

}
