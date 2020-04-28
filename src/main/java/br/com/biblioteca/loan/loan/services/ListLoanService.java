package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.loan.LoanReturnDTO;

import java.util.List;

@FunctionalInterface
public interface ListLoanService {

    List<LoanReturnDTO> findAll();
}
