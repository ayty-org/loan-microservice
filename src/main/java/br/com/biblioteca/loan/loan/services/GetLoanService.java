package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.loan.LoanReturnDTO;

@FunctionalInterface
public interface GetLoanService {

    LoanReturnDTO find(Long id);
}
