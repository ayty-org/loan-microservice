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
import feign.FeignException;
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
        }catch (FeignException.NotFound request){
            throw new FeignUserAppException(request.getMessage());
        }

        try {
            for (String book : loan.getBooks()) {
                getBook.bookId(book);
            }
        } catch (FeignException.NotFound request) {
            throw new FeignBookException(request.getMessage());
        }

        String idSpecific = "";
        for (String book : loan.getBooks()) {
            idSpecific += book;
            idSpecific += ",";
        }

        Loan loanApp = Loan.to(loan, idSpecific);
        loanRepository.save(loanApp);
        loanApp.setLoanSpecificID(gerarSpecificId(loanApp.getId()));
        loanRepository.save(loanApp);

        updateUserApp.updateUserApp(loanApp.getUserApp(), loanApp.getLoanSpecificID());

        for (String book : loan.getBooks()) {
            updateBook.updateBook(book, loanApp.getLoanSpecificID());
            updateBook.updateStatusBook(book,true);
        }
    }

    public static String gerarSpecificId(Long id) {
        return "00" + id;
    }
}
