package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.feign.GetBook;
import br.com.biblioteca.loan.feign.GetUserApp;
import br.com.biblioteca.loan.loan.BookDTO;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.UserAppDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ListLoanServiceImpl implements ListLoanService {

    private final LoanRepository loanRepository;
    private List<LoanReturnDTO> loans;
    private final GetBook getBook;
    private final GetUserApp getUserApp;

    @Override
    public List<LoanReturnDTO> findAll() {
        loans = new ArrayList<>();
        for (Loan loan : loanRepository.findAll()) {
            UserAppDTO userAppDTO = getUserApp.userId(loan.getUserApp());
            List<BookDTO> bookDTOS = getBook.bookAllId(loan.getLoanSpecificID());
            loans.add(LoanReturnDTO.from(loan, userAppDTO, bookDTOS));
        }
        return loans;
    }
}
