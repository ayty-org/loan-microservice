package br.com.biblioteca.loan.loan.v1;

import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.LoanSaveDTO;
import br.com.biblioteca.loan.loan.LoanUpdateDTO;
import br.com.biblioteca.loan.loan.services.DeleteLoanService;
import br.com.biblioteca.loan.loan.services.GetLoanService;
import br.com.biblioteca.loan.loan.services.ListLoanService;
import br.com.biblioteca.loan.loan.services.ListPageLoanService;
import br.com.biblioteca.loan.loan.services.SaveLoanService;
import br.com.biblioteca.loan.loan.services.UpdateLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/loan")
public class LoanControllerV1 {

    private final GetLoanService getLoanService;
    private final ListLoanService listLoanService;
    private final ListPageLoanService listPageLoanService;
    private final SaveLoanService saveLoanService;
    private final UpdateLoanService updateLoanService;
    private final DeleteLoanService deleteLoanService;

    @GetMapping(value = "/{id}") //lista emprestimos por id
    public LoanReturnDTO find(@PathVariable Long id) {
        return getLoanService.find(id);
    }

    @GetMapping //lista todos os emprestimos
    public List<LoanReturnDTO> findAll() {
        return listLoanService.findAll();
    }

    @GetMapping(path = "/page") //lista todas os emprestimos com paginação
    public Page<LoanReturnDTO> findPage(Pageable pageable) {
        return listPageLoanService.findPage(pageable);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping() //adiciona um emprestimo Book
    public void insert(@Valid @RequestBody LoanSaveDTO loanSaveDTO) {
        saveLoanService.insert(loanSaveDTO);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}") //atualizar uma emprestimo
    public void update(@Valid @RequestBody LoanUpdateDTO loanUpdateDTO, @PathVariable Long id) {
        updateLoanService.update(loanUpdateDTO, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}") //Deleta emprestimo
    public void delete(@PathVariable Long id) {
        deleteLoanService.delete(id);
    }
}
