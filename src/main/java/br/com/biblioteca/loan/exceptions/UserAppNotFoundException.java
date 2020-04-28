package br.com.biblioteca.loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAppNotFoundException extends RuntimeException {

    public UserAppNotFoundException() {
        super("Usuário não encontrado!");
    }
}
