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
public class LoanReturnDTO {

    private Long id;

    @NotEmpty
    private UserAppDTO userApp;

    @NotEmpty
    private List<BookDTO> books;

    @NotEmpty
    private String loanTime;

    public static LoanReturnDTO from(Loan loan, UserAppDTO userAppDTO, List<BookDTO> bookDTO) {
        return LoanReturnDTO
                .builder()
                .id(loan.getId())
                .userApp(userAppDTO)
                .loanTime(loan.getLoanTime())
                .books(bookDTO)
                .build();
    }

}
