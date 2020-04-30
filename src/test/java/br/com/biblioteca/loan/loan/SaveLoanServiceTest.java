package br.com.biblioteca.loan.loan;

import br.com.biblioteca.loan.exceptions.FeignBookException;
import br.com.biblioteca.loan.exceptions.FeignUserAppException;
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

import static br.com.biblioteca.loan.loan.builders.LoanSaveBuilder.createLoanSave;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por salvar um Loan")
public class SaveLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

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

        saveLoan.insert(createLoanSave().build());

        ArgumentCaptor<Loan> captorLoan = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository, times(2)).save(captorLoan.capture());

        Loan result = captorLoan.getValue();
        assertAll("Loan",
                () -> assertThat(result.getUserApp(), is("001")),
                () -> assertThat(result.getBook(), is("001,001,")),
                () -> assertThat(result.getLoanTime(), is("50 dias"))
        );

    }

    @Test
    @DisplayName("Deve lançar exceção quando o emprestimo não for encontrado")
    void shouldThrowFeignUserAppException() {
        saveLoan.insert(createLoanSave().build());
        when(getUserApp.userId(anyString())).thenThrow(new FeignUserAppException(""));
        assertThrows(FeignUserAppException.class, () -> this.getUserApp.userId(anyString()));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o emprestimo não for encontrado")
    void shouldThrowBookNotFoundException() {
        saveLoan.insert(createLoanSave().build());
        when(getBook.bookId(anyString())).thenThrow(new FeignBookException(""));
        assertThrows(FeignBookException.class, () -> this.getBook.bookId(anyString()));
    }
}
