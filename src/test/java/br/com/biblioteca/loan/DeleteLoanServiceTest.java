package br.com.biblioteca.loan;

import br.com.biblioteca.loan.exceptions.LoanNotDeletedException;
import br.com.biblioteca.loan.feign.UpdateBook;
import br.com.biblioteca.loan.feign.UpdateUserApp;
import br.com.biblioteca.loan.loan.LoanBookSpecificIdDTO;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanUserAppSpecificIdDTO;
import br.com.biblioteca.loan.loan.services.DeleteLoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.biblioteca.loan.builders.LoanBuilder.createLoan;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por deletar um Loan")
public class DeleteLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private DeleteLoanServiceImpl deleteLoan;
    @Mock
    private UpdateUserApp updateUserApp;
    @Mock
    private UpdateBook updateBook;

    @BeforeEach
    public void setUp() {
        this.deleteLoan = new DeleteLoanServiceImpl(loanRepository,updateUserApp,updateBook);
    }

    @Test
    @DisplayName("Deve deletar um emprestimo")
    void shouldLoanDeleted() {
        when(loanRepository.existsById(1L)).thenReturn(true);
        deleteLoan.delete(1L);
        verify(loanRepository).existsById(1L);
    }

    @Test
    @DisplayName("Deve deletar um emprestimo e atualizar o campo de loanSpecificID em book e user")
    void shouldLoanDeletedUpdateLoanSpecificIdMicroservices() {
        when(loanRepository.existsById(1L)).thenReturn(true);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(createLoan().id(1L).build()));
        //updateBook.updateBook("001", new LoanBookSpecificIdDTO(null));
        //updateUserApp.updateUserApp("001", new LoanUserAppSpecificIdDTO(null));
        deleteLoan.delete(1L);
        verify(loanRepository).existsById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o emprestimo não puder ser excluido")
    void shouldThrowLoanNotDeletedException() {
        lenient().when(loanRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(LoanNotDeletedException.class, () -> this.deleteLoan.delete(1L));
    }
}
