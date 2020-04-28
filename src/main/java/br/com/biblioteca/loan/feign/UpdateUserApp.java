package br.com.biblioteca.loan.feign;

import br.com.biblioteca.loan.loan.LoanUserAppSpecificIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(contextId = "UpdateUserApp", url = "http://localhost:9092/v2/api/user", name = "book-user")
public interface UpdateUserApp {

    @PutMapping(value = "/{userApp}")
    void updateUserApp(@PathVariable("userApp") String id, LoanUserAppSpecificIdDTO loanUserAppSpecificIdDTO);
}
