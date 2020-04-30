package br.com.biblioteca.loan.loan;

import br.com.biblioteca.loan.exceptions.LoanNotFoundException;
import br.com.biblioteca.loan.feign.GetBook;
import br.com.biblioteca.loan.feign.GetUserApp;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.services.GetLoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.biblioteca.loan.loan.builders.BookBuilder.createBook;
import static br.com.biblioteca.loan.loan.builders.LoanBuilder.createLoan;
import static br.com.biblioteca.loan.loan.builders.UserAppBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar um Loan")
public class GetLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    private GetLoanServiceImpl findLoan;
    @Mock
    private GetBook getBook;
    @Mock
    private GetUserApp getUserApp;

    @BeforeEach
    public void setUp() {
        this.findLoan = new GetLoanServiceImpl(loanRepository, getBook, getUserApp);
    }

    @Test
    @DisplayName("Deve retornar um emprestimo")
    void shouldFindByIdLoan() {

        when(loanRepository.findById(anyLong())).thenReturn(
                Optional.of(createLoan().loanTime("Loan Teste GET").build())
        );

        when(getUserApp.userId(anyString())).thenReturn(createUserApp().build());

        when(getBook.bookAllId(anyString())).thenReturn(Stream.of(createBook().author("Author Teste GET 01").build(),
                createBook().author("Author Teste GET 02").build(),
                createBook().author("Author Teste GET 03").build()).collect(Collectors.toList()));

        LoanReturnDTO result = this.findLoan.find(1L);

        //verificação
        assertAll("Loan",
                () -> assertThat(result.getUserApp().getName(), is("teste nome")),
                () -> assertThat(result.getBooks().get(0).getTitle(), is("teste title")),
                () -> assertThat(result.getLoanTime(), is("Loan Teste GET"))
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o emprestimo não for encontrado")
    void shouldThrowBookNotFoundException() {
        when(loanRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(LoanNotFoundException.class, () -> this.findLoan.find(1L));
    }
}
