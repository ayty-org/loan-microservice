package br.com.biblioteca.loan.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class LoanSaveDTO {

    private Long id;

    @NotEmpty
    private String userApp;

    @NotEmpty
    private List<String> books;

    @NotEmpty
    private String loanTime;

    private String loanSpecificID;
}
