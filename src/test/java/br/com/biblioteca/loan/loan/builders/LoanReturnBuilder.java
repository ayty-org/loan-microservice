package br.com.biblioteca.loan.loan.builders;

import br.com.biblioteca.loan.loan.BookDTO;
import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.UserAppDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanReturnBuilder {

    public static LoanReturnDTO.Builder createLoanReturn() {
        return LoanReturnDTO
                .builder()
                .userApp(userApp())
                .books(listBook())
                .loanTime("50 dias");
    }

    public static UserAppDTO userApp() {
        return new UserAppDTO(1L, "teste nome", 21, "46356357", "001", "001");
    }

    public static List<BookDTO> listBook() {
        BookDTO bookDTO = new BookDTO(1L, "teste title", "teste resume", "teste isbn", "teste author", LocalDate.ofEpochDay(25 - 04 - 2020), "001", "001");
        List<BookDTO> books = new ArrayList<>();
        books.add(bookDTO);
        return books;
    }
}
