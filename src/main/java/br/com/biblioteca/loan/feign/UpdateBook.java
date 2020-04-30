package br.com.biblioteca.loan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(contextId = "UpdateBook", url = "http://localhost:9092/v1/api/book", name = "book-user")
public interface UpdateBook {

    @PutMapping(value = "/updateLoanSpecificId/{id}")
    void updateBook(@PathVariable String id, String loanBookSpecificIdDTO);

    @PutMapping(value = "/updateStatusBook/{id}")
    void updateStatusBook(@PathVariable String id, boolean status);
}
