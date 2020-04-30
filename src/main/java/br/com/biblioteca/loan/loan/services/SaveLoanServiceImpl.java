package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.exceptions.FeignBookException;
import br.com.biblioteca.loan.exceptions.FeignUserAppException;
import br.com.biblioteca.loan.feign.GetBook;
import br.com.biblioteca.loan.feign.GetUserApp;
import br.com.biblioteca.loan.feign.UpdateBook;
import br.com.biblioteca.loan.feign.UpdateUserApp;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanSaveDTO;
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

    @Override
    public void insert(LoanSaveDTO loan) {
        try {
            getUserApp.userId(loan.getUserApp());
        }catch (feign.FeignException.NotFound request){
            throw new FeignUserAppException(request.getMessage());
        }

        try {
            for (BookSaveDTO book : loan.getBooks()) {
                getBook.bookId(book.getSpecificID());
            }
        } catch (feign.FeignException.NotFound request) {
            throw new FeignBookException(request.getMessage());
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

        updateUserApp.updateUserApp(loanApp.getUserApp(), loanApp.getLoanSpecificID());

        for (BookSaveDTO book : loan.getBooks()) {
            updateBook.updateBook(book.getSpecificID(), loanApp.getLoanSpecificID());
            updateBook.updateStatusBook(book.getSpecificID(),true);
        }
    }

    public static String gerarSpecificId(Long id) {
        return "00" + id;
    }
}
