package br.com.biblioteca.loan;

import br.com.biblioteca.loan.feign.GetBook;
import br.com.biblioteca.loan.feign.GetUserApp;
import br.com.biblioteca.loan.feign.UpdateBook;
import br.com.biblioteca.loan.feign.UpdateUserApp;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.services.SaveLoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.biblioteca.loan.builders.LoanSaveBuilder.createLoanSave;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por salvar um Loan")
public class SaveLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private SaveLoanServiceImpl saveLoan;
    @Mock
    private GetBook getBook;
    @Mock
    private GetUserApp getUserApp;
    @Mock
    private UpdateUserApp updateUserApp;
    @Mock
    private UpdateBook updateBook;


    @BeforeEach
    public void setUp() {
        this.saveLoan = new SaveLoanServiceImpl(loanRepository, getBook, getUserApp, updateUserApp, updateBook);
    }

    @Test
    @DisplayName("Deve salvar um Loan")
    void shouldSaveLoan() {

        //execução
        saveLoan.insert(createLoanSave().build());

        //preparação
        ArgumentCaptor<Loan> captorLoan = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository, times(2)).save(captorLoan.capture());

        Loan result = captorLoan.getValue();

        //verificação
        assertAll("Loan",
                () -> assertThat(result.getUserApp(), is("001")),
                () -> assertThat(result.getBook(), is("001001")),
                () -> assertThat(result.getLoanTime(), is("50 dias"))
        );
    }
}
