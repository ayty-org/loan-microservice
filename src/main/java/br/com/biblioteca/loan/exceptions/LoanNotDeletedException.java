package br.com.biblioteca.loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoanNotDeletedException extends RuntimeException {

    public LoanNotDeletedException() {
        super("Emprestimo não deletado!");
    }
}

