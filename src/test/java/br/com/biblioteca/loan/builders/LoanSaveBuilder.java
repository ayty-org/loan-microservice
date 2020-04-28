package br.com.biblioteca.loan.builders;

import br.com.biblioteca.loan.loan.BookDTO;
import br.com.biblioteca.loan.loan.BookSaveDTO;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanSaveDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanSaveBuilder {

    public static LoanSaveDTO.Builder createLoanSave(){
        return LoanSaveDTO
                .builder()
                .userApp("001")
                .books(listBook())
                .loanTime("50 dias")
                .loanSpecificID("001");
    }

    public static List<BookSaveDTO> listBook(){
        BookSaveDTO book01 = new BookSaveDTO("001");
        BookSaveDTO book02 = new BookSaveDTO("001");
        List<BookSaveDTO> books = new ArrayList<>();
        books.add(book01);
        books.add(book02);
        return books;
    }
}
