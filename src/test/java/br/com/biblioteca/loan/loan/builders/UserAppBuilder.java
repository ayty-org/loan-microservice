package br.com.biblioteca.loan.loan.builders;

import br.com.biblioteca.loan.loan.UserAppDTO;

public class UserAppBuilder {

    public static UserAppDTO.Builder createUserApp() {
        return UserAppDTO
                .builder()
                .name("teste nome")
                .age(20)
                .fone("teste fone")
                .specificID("001")
                .loanSpecificID("001");
    }
}
