package librarymanagement;

public class BookCopy {
    private final int copyId;
    private final Book book;


    public BookCopy(int copyId, Book book) {
        this.copyId = copyId;
        this.book = book;
    }

    public int getCopyId() {
        return copyId;
    }

    public Book getBook() {
        return book;
    }
}
