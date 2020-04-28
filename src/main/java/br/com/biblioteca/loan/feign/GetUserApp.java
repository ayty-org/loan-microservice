package br.com.biblioteca.loan.feign;

import br.com.biblioteca.loan.loan.UserAppDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "GetUserApp", url = "http://localhost:9092/v1/api/user", name = "book-user")
public interface GetUserApp {

    @GetMapping(value = "/getUserSpecificId/{id}")
    UserAppDTO userId(@PathVariable String id);
}
