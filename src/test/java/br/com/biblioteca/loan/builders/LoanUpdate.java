package br.com.biblioteca.loan.builders;

import br.com.biblioteca.loan.loan.BookDTO;
import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.LoanUpdateDTO;
import br.com.biblioteca.loan.loan.UserAppDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanUpdate {

    public static LoanUpdateDTO.Builder createLoanUpdate(){
        return LoanUpdateDTO
                .builder()
                .loanTime("50 dias");
    }

}
