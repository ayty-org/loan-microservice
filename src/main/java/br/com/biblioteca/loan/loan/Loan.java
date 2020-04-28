package br.com.biblioteca.loan.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userApp;

    private String book;

    private String loanTime;

    private String loanSpecificID;

    public static Loan to(LoanSaveDTO saveDTO, String bookId) {
        return Loan
                .builder()
                .id(saveDTO.getId())
                .userApp(saveDTO.getUserApp())
                .book(bookId)
                .loanTime(saveDTO.getLoanTime())
                .loanSpecificID(saveDTO.getLoanSpecificID())
                .build();
    }
}
