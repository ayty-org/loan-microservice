package br.com.biblioteca.loan;

import br.com.biblioteca.loan.loan.Loan;
import br.com.biblioteca.loan.loan.LoanReturnDTO;
import br.com.biblioteca.loan.loan.services.DeleteLoanService;
import br.com.biblioteca.loan.loan.services.GetLoanService;
import br.com.biblioteca.loan.loan.services.ListLoanService;
import br.com.biblioteca.loan.loan.services.ListPageLoanService;
import br.com.biblioteca.loan.loan.services.SaveLoanService;
import br.com.biblioteca.loan.loan.services.UpdateLoanService;
import br.com.biblioteca.loan.loan.v1.LoanControllerV1;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static br.com.biblioteca.loan.builders.LoanBuilder.createLoan;
import static br.com.biblioteca.loan.builders.LoanReturnBuilder.createLoanReturn;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LoanControllerV1.class)
@DisplayName("Valida funcionalidade do Controller Loan")
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GetLoanService getLoanService;
    @MockBean
    private ListLoanService listLoanService;
    @MockBean
    private ListPageLoanService listPageLoanService;
    @MockBean
    private SaveLoanService saveLoanService;
    @MockBean
    private UpdateLoanService updateLoanService;
    @MockBean
    private DeleteLoanService deleteLoanService;

    @Test
    @DisplayName("Pesquisa emprestimos por id")
    void whenValidGetIdLoan_thenReturnsLoan() throws Exception { //pesquisa por Loan

        when(getLoanService.find(anyLong())).thenReturn(createLoanReturn().id(1L).build());

        mockMvc.perform(get("/v1/api/loan/{id}",1L)
                .accept(MediaType.APPLICATION_JSON ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.userApp.name", is("teste nome")))
                .andExpect(jsonPath("$.userApp.age", is(21)))
                .andExpect(jsonPath("$.userApp.fone", is("46356357")))
                .andExpect(jsonPath("$.userApp.specificID", is("001")))
                .andExpect(jsonPath("$.userApp.loanSpecificID", is("001")))
                .andExpect(jsonPath("$.books[0].title", is("teste title")))
                .andExpect(jsonPath("$.books[0].resume", is("teste resume")))
                .andExpect(jsonPath("$.books[0].isbn", is("teste isbn")))
                .andExpect(jsonPath("$.books[0].author", is("teste author")))
                .andExpect(jsonPath("$.books[0].yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$.books[0].specificID", is("001")))
                .andExpect(jsonPath("$.books[0].loanSpecificID", is("001")))
                .andExpect(jsonPath("$.loanTime", is("50 dias")));
    }

    @Test
    @DisplayName("Pesquisa lista de emprestimo")
    void whenValidListLoan_thenReturnsLoan() throws Exception { //pesquisa todos os Loans

        when(listLoanService.findAll()).thenReturn(Lists.newArrayList(
                createLoanReturn().id(1L).loanTime("10 dias").build(), createLoanReturn().id(2L).loanTime("20 dias").build()
        ));

        mockMvc.perform(get("/v1/api/loan")
                .accept(MediaType.APPLICATION_JSON ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].userApp.name", is("teste nome")))
                .andExpect(jsonPath("[0].userApp.age", is(21)))
                .andExpect(jsonPath("$[0].userApp.fone", is("46356357")))
                .andExpect(jsonPath("$[0].userApp.specificID", is("001")))
                .andExpect(jsonPath("$[0].userApp.loanSpecificID", is("001")))
                .andExpect(jsonPath("$[0].books[0].title", is("teste title")))
                .andExpect(jsonPath("$[0].books[0].resume", is("teste resume")))
                .andExpect(jsonPath("$[0].books[0].isbn", is("teste isbn")))
                .andExpect(jsonPath("$[0].books[0].author", is("teste author")))
                .andExpect(jsonPath("$[0].books[0].specificID", is("001")))
                .andExpect(jsonPath("$[0].books[0].loanSpecificID", is("001")))
                .andExpect(jsonPath("$[0].loanTime", is("10 dias")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].userApp.name", is("teste nome")))
                .andExpect(jsonPath("$[1].userApp.age", is(21)))
                .andExpect(jsonPath("$[1].userApp.fone", is("46356357")))
                .andExpect(jsonPath("$[1].books[0].title", is("teste title")))
                .andExpect(jsonPath("$[1].books[0].resume", is("teste resume")))
                .andExpect(jsonPath("$[1].books[0].isbn", is("teste isbn")))
                .andExpect(jsonPath("$[1].books[0].author", is("teste author")))
                .andExpect(jsonPath("$[1].books[0].yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$[1].books[0].specificID", is("001")))
                .andExpect(jsonPath("$[1].books[0].loanSpecificID", is("001")))
                .andExpect(jsonPath("$[1].loanTime", is("20 dias")));
    }

    @Test
    @DisplayName("Pesquisa emprestimo com paginação")
    void whenValidListPageLoan_thenReturnsLoanPage() throws Exception { //pesquisa todos os Loans com paginanação

        Page<LoanReturnDTO> loanPage = new PageImpl<>(Collections.singletonList(createLoanReturn().id(1L).build()));
        Pageable pageable = PageRequest.of(0,2);
        when(listPageLoanService.findPage(pageable)).thenReturn(loanPage);

        mockMvc.perform(get("/v1/api/loan/page/?page=0&size=2")
                .accept(MediaType.APPLICATION_JSON ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].userApp.name", is("teste nome")))
                .andExpect(jsonPath("$.content[0].userApp.age", is(21)))
                .andExpect(jsonPath("$.content[0].userApp.fone", is("46356357")))
                .andExpect(jsonPath("$.content[0].books[0].title", is("teste title")))
                .andExpect(jsonPath("$.content[0].books[0].resume", is("teste resume")))
                .andExpect(jsonPath("$.content[0].books[0].isbn", is("teste isbn")))
                .andExpect(jsonPath("$.content[0].books[0].author", is("teste author")));
    }

    @Test
    @DisplayName("Salva um emprestimo")
    void whenValidSaveLoan_thenReturns201() throws Exception { //insere emprestimo
        mockMvc.perform(post("/v1/api/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("loanDTO.json")))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Edita um emprestimo")
    void whenValidUpdateLoan_thenReturnsLoan() throws Exception { //atualiza un emprestimo
        mockMvc.perform(put("/v1/api/loan/{id}", 1L)
                .content(readJson("loanUpdate.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deleta livro")
    void whenValidDeleteLoan_thenReturns204() throws Exception{ // deleta emprestimo
        mockMvc.perform(delete("/v1/api/loan/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public static String readJson(String file) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/java/resources/json/" + file).toAbsolutePath());
        return new String(bytes);
    }
}