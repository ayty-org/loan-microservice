package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.feign.GetBook;
import br.com.biblioteca.loan.feign.GetUserApp;
import br.com.biblioteca.loan.loan.BookDTO;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.UserAppDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ListPageLoanServiceImpl implements ListPageLoanService {

    private final LoanRepository loanRepository;
    private List<LoanReturnDTO> loans;
    private final GetBook getBook;
    private final GetUserApp getUserApp;

    @Override
    public Page<LoanReturnDTO> findPage(Pageable pageable) {
        loans = new ArrayList<>();
        for (Loan loan : loanRepository.findAll()) {
            UserAppDTO userAppDTO = getUserApp.userId(loan.getUserApp());
            List<BookDTO> bookDTOS = getBook.bookAllId(loan.getLoanSpecificID());
            loans.add(LoanReturnDTO.from(loan, userAppDTO, bookDTOS));
        }
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > loans.size() ? loans.size() : (start + pageable.getPageSize());
        return new PageImpl(loans.subList(start,end), pageable, loans.size());
    }
}
