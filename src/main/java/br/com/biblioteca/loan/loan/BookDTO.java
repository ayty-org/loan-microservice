package br.com.biblioteca.loan.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class BookDTO {

    private Long id;

    @NotEmpty
    private String title; //titulo

    @Size(max = 500)
    private String resume; //resumo

    @NotEmpty
    private String isbn;

    @NotEmpty
    private String author; //autor

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate yearBook; //ano

    private String specificID;

    private String loanSpecificID;

}
