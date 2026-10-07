import librarymanagement.Book;
import librarymanagement.Library;
import librarymanagement.Loan;
import librarymanagement.Member;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Library library = new Library();

    Book book = new Book(1, "Clean Code", "Robert C. Martin");
    Member member = new Member(101, "Alice");

    library.addBook(book);
    library.addBookCopy(11, 1);
    library.addBookCopy(12, 1);
    library.addMember(member);

    check("Initially available copies",
            2, library.getAvailableCopies(1).size());

    LocalDate borrowedOn = LocalDate.of(2026, 10, 8);
    Loan loan = library.borrowBook(101, 11, borrowedOn);

    check("Available copies after borrowing",
            1, library.getAvailableCopies(1).size());

    check("Member's active loan count",
            1, member.getActiveLoanCount());

    check("Active loans returned by Library",
            1, library.getActiveLoansForMember(101).size());

    LocalDate expectedDueDate = LocalDate.of(2026, 10, 22);
    if (!expectedDueDate.equals(loan.getDueOn())) {
        throw new AssertionError(
                "Expected due date " + expectedDueDate
                        + ", but got " + loan.getDueOn());
    }
    System.out.println("PASS: Due date = " + loan.getDueOn());

    System.out.println("All borrowing checks passed.");



    library.returnBook(11);

    check("Available copies after return",
            2, library.getAvailableCopies(1).size());

    check("Member's active loan count after return",
            0, member.getActiveLoanCount());

    check("Active loans after return",
            0, library.getActiveLoansForMember(101).size());

    if (loan.isActive()) {
        throw new AssertionError("Returned loan should be inactive");
    }
    System.out.println("PASS: Original loan is inactive");



    // Returning the same copy again must fail.
    try {
        library.returnBook(11);
        throw new AssertionError("A second return should fail");
    } catch (IllegalStateException e) {
        System.out.println("PASS: Repeated return rejected");
    }

// Borrowing a returned copy again must succeed.
    Loan secondLoan = library.borrowBook(101, 11, borrowedOn);

// Borrowing that copy while it is already borrowed must fail.
    try {
        library.borrowBook(101, 11, borrowedOn);
        throw new AssertionError("Borrowing an unavailable copy should fail");
    } catch (IllegalStateException e) {
        System.out.println("PASS: Duplicate borrowing rejected");
    }

    check("Active loan count after rejected borrowing",
            1, member.getActiveLoanCount());

    check("Available copies after rejected borrowing",
            1, library.getAvailableCopies(1).size());

    if (loan.isActive() || !secondLoan.isActive()) {
        throw new AssertionError(
                "Old loan should remain inactive; new loan should be active");
    }
    System.out.println("PASS: Reborrowing creates a separate active loan");




    library.addBookCopy(13, 1);
    library.addBookCopy(14, 1);

    library.borrowBook(101, 12, borrowedOn);
    library.borrowBook(101, 13, borrowedOn);

    check("Active loans at limit", 3, member.getActiveLoanCount());

// Copy 14 is available, but Alice has reached her limit.
    try {
        library.borrowBook(101, 14, borrowedOn);
        throw new AssertionError("A fourth active loan should fail");
    } catch (IllegalStateException e) {
        System.out.println("PASS: Fourth active loan rejected");
    }

    check("Active loans after rejection",
            3, member.getActiveLoanCount());

    check("Available copies after rejection",
            1, library.getAvailableCopies(1).size());

// Returning one copy should allow another borrowing.
    library.returnBook(12);
    library.borrowBook(101, 14, borrowedOn);

    check("Active loans after return and new borrowing",
            3, member.getActiveLoanCount());

    System.out.println("PASS: Returning a copy frees a borrowing slot");
}

        private static void check(String label, int expected, int actual) {
            if (expected != actual) {
                throw new AssertionError(
                        label + ": expected " + expected + ", but got " + actual);
            }
            System.out.println("PASS: " + label + " = " + actual);
        }

