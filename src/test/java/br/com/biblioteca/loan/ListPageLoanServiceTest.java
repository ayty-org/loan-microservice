package br.com.biblioteca.loan;

import br.com.biblioteca.loan.feign.GetBook;
import br.com.biblioteca.loan.feign.GetUserApp;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.services.ListPageLoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.biblioteca.loan.builders.BookBuilder.createBook;
import static br.com.biblioteca.loan.builders.LoanBuilder.createLoan;
import static br.com.biblioteca.loan.builders.UserAppBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar Loans por paginação")
public class ListPageLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    private ListPageLoanServiceImpl listPageLoan;

    @Mock
    private GetBook getBook;

    @Mock
    private GetUserApp getUserApp;


    @BeforeEach
    public void setUp() {
        this.listPageLoan = new ListPageLoanServiceImpl(loanRepository, getBook, getUserApp);
    }

    @Test
    @DisplayName("Deve retornar todos os emprestimos com paginação")
    void shouldFindAllBook() {

        when(loanRepository.findAll()).thenReturn(Arrays.asList(
                createLoan().build(), createLoan().build()
        ));

        when(getUserApp.userId(anyString())).thenReturn(createUserApp().build());

        when(getBook.bookAllId(anyString())).thenReturn(Stream.of(createBook().author("Author Teste GET 01").build()).collect(Collectors.toList()));

        Pageable pageable = PageRequest.of(0, 2);

        Page<LoanReturnDTO> loanPage = listPageLoan.findPage(pageable);

        assertAll("loan",
                () -> assertThat(loanPage.getNumber(), is(0)),
                () -> assertThat(loanPage.getSize(), is(2)),
                () -> assertThat(loanPage.getContent().get(0).getUserApp().getName(), is("teste nome")),
                () -> assertThat(loanPage.getContent().get(0).getBooks().get(0).getTitle(), is("teste title")),
                () -> assertThat(loanPage.getContent().get(0).getLoanTime(), is("50 dias")),
                () -> assertThat(loanPage.getContent().get(1).getUserApp().getName(), is("teste nome")),
                () -> assertThat(loanPage.getContent().get(1).getBooks().get(0).getTitle(), is("teste title")),
                () -> assertThat(loanPage.getContent().get(1).getLoanTime(), is("50 dias"))
        );

    }
}
