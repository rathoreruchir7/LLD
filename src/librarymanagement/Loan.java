package librarymanagement;

import java.time.LocalDate;

public class Loan {
    private int loanId;
    private BookCopy copy;
    private Member member;
    private LocalDate borrowedOn;
    private LocalDate dueOn;
    private boolean returned;

    public Loan (int id, BookCopy copy, Member member, LocalDate borrowedOn) {
        this.loanId = id;
        this.copy = copy;
        this.member = member;
        this.borrowedOn = borrowedOn;
        this.dueOn = borrowedOn.plusDays(14);
        this.returned = false;
    }

    public boolean isActive () {
        return !returned;
    }

    public void markReturned () {
        if (returned) {
            throw new IllegalStateException("Loan already returned");
        }
        returned = true;
    }

    public BookCopy getCopy() {
        return copy;
    }

    public int getLoanId() {
        return loanId;
    }

    public LocalDate getDueOn() {
        return dueOn;
    }
}
