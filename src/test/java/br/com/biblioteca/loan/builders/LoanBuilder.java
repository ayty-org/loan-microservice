package br.com.biblioteca.loan.builders;

import br.com.biblioteca.loan.loan.Loan;

public class LoanBuilder {

    public static Loan.Builder createLoan(){
        return Loan
                .builder()
                .userApp("001")
                .book("001")
                .loanTime("50 dias")
                .loanSpecificID("001");
    }
}
