package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.loan.LoanUpdateDTO;

@FunctionalInterface
public interface UpdateLoanService {

    void update(LoanUpdateDTO loan, Long id);
}
