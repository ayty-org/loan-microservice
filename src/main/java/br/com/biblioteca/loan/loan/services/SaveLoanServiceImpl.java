package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.exceptions.BookNotFoundException;
import br.com.biblioteca.loan.exceptions.UserAppNotFoundException;
import br.com.biblioteca.loan.feign.GetBook;
import br.com.biblioteca.loan.feign.GetUserApp;
import br.com.biblioteca.loan.feign.UpdateBook;
import br.com.biblioteca.loan.feign.UpdateUserApp;
import br.com.biblioteca.loan.loan.BookSaveDTO;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanBookSpecificIdDTO;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanSaveDTO;
import br.com.biblioteca.loan.loan.LoanUserAppSpecificIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveLoanServiceImpl implements SaveLoanService {

    private final LoanRepository loanRepository;
    private final GetBook getBook;
    private final GetUserApp getUserApp;
    private final UpdateUserApp updateUserApp;
    private final UpdateBook updateBook;
    private LoanUserAppSpecificIdDTO loanUserAppSpecificIdDTO;
    private LoanBookSpecificIdDTO loanBookSpecificIdDTO;

    @Override
    public void insert(LoanSaveDTO loan) {
        try {
            getUserApp.userId(loan.getUserApp());
        } catch (Exception e) {
            throw new UserAppNotFoundException();
        }

        try {
            for (BookSaveDTO book : loan.getBooks()) {
                getBook.bookId(book.getSpecificID());
            }
        } catch (Exception e) {
            throw new BookNotFoundException();
        }

        String idSpecific = "";
        for (BookSaveDTO book : loan.getBooks()) {
            idSpecific += book.getSpecificID();
            idSpecific += ",";
        }

        Loan loanApp = Loan.to(loan, idSpecific);
        loanRepository.save(loanApp);
        loanApp.setLoanSpecificID(gerarSpecificId(loanApp.getId()));
        loanRepository.save(loanApp);

        loanUserAppSpecificIdDTO = new LoanUserAppSpecificIdDTO();
        loanBookSpecificIdDTO = new LoanBookSpecificIdDTO();
        loanUserAppSpecificIdDTO.setLoanSpecificID(loanApp.getLoanSpecificID());
        loanBookSpecificIdDTO.setLoanSpecificID(loanApp.getLoanSpecificID());
        updateUserApp.updateUserApp(loanApp.getUserApp(), loanUserAppSpecificIdDTO);

        for (BookSaveDTO book : loan.getBooks()) {
            updateBook.updateBook(book.getSpecificID(), loanBookSpecificIdDTO);
        }
    }

    public static String gerarSpecificId(Long id) {
        return "00" + id;
    }
}
