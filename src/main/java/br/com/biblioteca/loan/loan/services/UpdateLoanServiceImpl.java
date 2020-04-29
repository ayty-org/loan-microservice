package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.exceptions.FeignBookException;
import br.com.biblioteca.loan.exceptions.LoanNotFoundException;
import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanRepository;
import br.com.biblioteca.loan.loan.LoanUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateLoanServiceImpl implements UpdateLoanService {

    private final LoanRepository loanRepository;

    @Override
    public void update(LoanUpdateDTO obj, Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(LoanNotFoundException::new);
        loan.setLoanTime(obj.getLoanTime());
        loanRepository.save(loan);
    }
}
