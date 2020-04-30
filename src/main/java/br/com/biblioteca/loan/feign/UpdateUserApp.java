package br.com.biblioteca.loan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(contextId = "UpdateUserApp", url = "http://localhost:9092/v1/api/user", name = "book-user")
public interface UpdateUserApp {

    @PutMapping(value = "/updateLoanSpecificId/{id}")
    void updateUserApp(@PathVariable String id, String loanUserAppSpecificIdDTO);
}
