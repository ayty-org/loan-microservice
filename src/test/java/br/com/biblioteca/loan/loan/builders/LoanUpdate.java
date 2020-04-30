package br.com.biblioteca.loan.loan.builders;

import br.com.biblioteca.loan.loan.LoanUpdateDTO;

public class LoanUpdate {

    public static LoanUpdateDTO.Builder createLoanUpdate() {
        return LoanUpdateDTO
                .builder()
                .loanTime("50 dias");
    }

}
