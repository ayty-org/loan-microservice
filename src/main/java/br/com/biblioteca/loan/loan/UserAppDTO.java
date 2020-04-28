package br.com.biblioteca.loan.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class UserAppDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private int age;

    @NotEmpty
    private String fone;

    private String specificID;

    private String loanSpecificID;
}
