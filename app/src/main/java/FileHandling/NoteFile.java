package FileHandling;

class NoteFile extends File {

    NoteFile(String name) {
        super(name);
    }

    @Override
    int getType() {
        return 0;
    }
}
