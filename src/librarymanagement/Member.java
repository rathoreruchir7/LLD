package librarymanagement;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberId;
    private String name;
    private List<Loan> loans;

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        loans = new ArrayList<>();
    }

    public int getActiveLoanCount() {
        int count = 0;
        for (Loan l: loans) {
            if (l.isActive()) {
                count++;
            }
        }
        return count;
    }

    public void addLoan (Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cant be null");
        }
        loans.add(loan);
    }

    public int getMemberId() {
        return memberId;
    }

    public List<Loan> getActiveLoans() {
        List<Loan> list = new ArrayList<>();
        for(Loan l: loans) {
            if (l.isActive()) {
                list.add(l);
            }
        }
        return list;
    }
}
