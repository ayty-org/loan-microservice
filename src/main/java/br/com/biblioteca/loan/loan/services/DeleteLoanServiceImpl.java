package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.exceptions.LoanNotDeletedException;
import br.com.biblioteca.loan.exceptions.LoanNotFoundException;
import br.com.biblioteca.loan.feign.UpdateBook;
import br.com.biblioteca.loan.feign.UpdateUserApp;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteLoanServiceImpl implements DeleteLoanService {

    private final LoanRepository loanRepository;
    private final UpdateUserApp updateUserApp;
    private final UpdateBook updateBook;

    @Override
    public void delete(Long id) {

        if (!loanRepository.existsById(id)) {
            throw new LoanNotDeletedException();
        }
        Loan loanApp = loanRepository.findById(id).orElseThrow(LoanNotFoundException::new);
        updateUserApp.updateUserApp(loanApp.getUserApp(),"null");
        for (String i: ids(loanApp.getBook())){
            updateBook.updateBook(i, "null");
            updateBook.updateStatusBook(i,false);
        }
        loanRepository.deleteById(id);
    }

    private static String[] ids(String ids){
        return ids.split(",");
    }
}
