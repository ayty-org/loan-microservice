package br.com.biblioteca.loan.loan.builders;

import br.com.biblioteca.loan.loan.LoanSaveDTO;

import java.util.ArrayList;
import java.util.List;

public class LoanSaveBuilder {

    public static LoanSaveDTO.Builder createLoanSave() {
        return LoanSaveDTO
                .builder()
                .userApp("001")
                .books(listBook())
                .loanTime("50 dias")
                .loanSpecificID("001");
    }

    public static List<String> listBook() {
        String book01 = "001";
        String book02 = "001";
        List<String> books = new ArrayList<>();
        books.add(book01);
        books.add(book02);
        return books;
    }
}
