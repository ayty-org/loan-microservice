package br.com.biblioteca.loan.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class LoanDTO {

    private Long id;

    @NotEmpty
    private String userApp;

    @NotEmpty
    private String book;

    @NotEmpty
    private String loanTime;

    private String loanSpecificID;

    public static LoanDTO from(Loan loan) {
        return LoanDTO
                .builder()
                .id(loan.getId())
                .userApp(loan.getUserApp())
                .book(loan.getBook())
                .loanTime(loan.getLoanTime())
                .loanSpecificID(loan.getLoanSpecificID())
                .build();

    }

    public static Page<LoanDTO> fromPage(Page<Loan> pages) {
        return pages.map(LoanDTO::from);
    }

}
