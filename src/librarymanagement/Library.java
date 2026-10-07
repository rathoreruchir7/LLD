package librarymanagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, BookCopy> copies = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();
    private Map<Integer, Loan> loans = new HashMap<>();
    private int nextLoanId = 1;

    public Library() {

    }

    public void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        int id = member.getMemberId();
        if (members.containsKey(id)) {
            throw new IllegalArgumentException("Member ID already exists");
        }
        members.put(id, member);
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        int id = book.getBookId();
        if (books.containsKey(id)) {
            throw new IllegalArgumentException("Book id already exist");
        }
        books.put(id, book);
    }

    public void addBookCopy(int copyId, int bookId) {
        if (!books.containsKey(bookId)) {
            throw new IllegalArgumentException("Book id does not exist");
        }
        if (copies.containsKey(copyId)) {
            throw new IllegalArgumentException("Copy Id already exists");
        }
        Book b = books.get(bookId);
        BookCopy c = new BookCopy(copyId, b);
        copies.put(copyId, c);
    }

    private Loan findActiveLoanForCopy(int copyId) {
        for (Loan l: loans.values()) {
            if (l.isActive() && l.getCopy().getCopyId()==copyId) {
                return l;
            }
        }
        return null;
    }

    public Loan borrowBook(int memberId, int copyId, LocalDate borrowedOn) {
        if (!members.containsKey(memberId)) throw new IllegalArgumentException("Member does not exist");
        if (!copies.containsKey(copyId)) throw new IllegalArgumentException("Copy id does not exist");
        if (borrowedOn == null) throw new IllegalArgumentException("Borrowed date is null");

        Member member = members.get(memberId);
        if (member.getActiveLoanCount() >= 3) {
            throw new IllegalStateException("Member already has 3 or more active loans");
        }
        if (findActiveLoanForCopy(copyId) != null) {
            throw new IllegalStateException("Loan on this copy Id already exists.");
        }

        BookCopy c = copies.get(copyId);
        Loan loan = new Loan(nextLoanId++, c, member, borrowedOn);
        loans.put(loan.getLoanId(), loan);
        member.addLoan(loan);
        return loan;
    }

    public void returnBook(int copyId) {
        if (!copies.containsKey(copyId)) throw new IllegalArgumentException("Copy id does not exist");

        Loan loan = findActiveLoanForCopy(copyId);
        if (loan == null) {
            throw new IllegalStateException("Copy is not currently borrowed");
        }
        loan.markReturned();
    }

    public List<BookCopy> getAvailableCopies(int bookId) {
        if (!books.containsKey(bookId)) throw new IllegalArgumentException("Book id does not exist");
        List<BookCopy> list = new ArrayList<>();

        Book b = books.get(bookId);
        for(BookCopy c: copies.values()) {
            if (c.getBook() == b && findActiveLoanForCopy(c.getCopyId())==null) {
                list.add(c);
            }
        }
        return list;
    }

    public List<Loan> getActiveLoansForMember(int memberId) {
        if (!members.containsKey(memberId)) throw new IllegalArgumentException("Member does not exist");
        Member member = members.get(memberId);
        return member.getActiveLoans();
    }
}
