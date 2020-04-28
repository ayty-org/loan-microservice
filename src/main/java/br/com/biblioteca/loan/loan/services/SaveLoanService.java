package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.loan.LoanSaveDTO;

@FunctionalInterface
public interface SaveLoanService {

    void insert(LoanSaveDTO loanSaveDTO);

}
