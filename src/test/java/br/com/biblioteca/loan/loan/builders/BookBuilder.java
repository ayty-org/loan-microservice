package br.com.biblioteca.loan.loan.builders;

import br.com.biblioteca.loan.loan.BookDTO;

import java.time.LocalDate;

public class BookBuilder {

    public static BookDTO.Builder createBook() {
        return BookDTO
                .builder()
                .title("teste title")
                .resume("teste resume")
                .isbn("teste isbn")
                .author("teste author")
                .yearBook(LocalDate.ofEpochDay(25 - 04 - 2020))
                .specificID("001")
                .loanSpecificID("001");
    }

}
