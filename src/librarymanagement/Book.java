package librarymanagement;

public class Book {
    private final int bookId;
    private final String title;
    private final String author;


    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }
}
