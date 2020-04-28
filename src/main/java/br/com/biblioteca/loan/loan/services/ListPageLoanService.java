package br.com.biblioteca.loan.loan.services;

import br.com.biblioteca.loan.loan.LoanReturnDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface ListPageLoanService {

    Page<LoanReturnDTO> findPage(Pageable pageable);
}
