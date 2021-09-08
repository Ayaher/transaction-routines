package br.com.bbex.transactions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.bbex.transactions.controller.TransactionCrontroller;
import br.com.bbex.transactions.controller.request.TransactionRequest;
import br.com.bbex.transactions.model.Account;
import br.com.bbex.transactions.model.Transaction;
import br.com.bbex.transactions.repository.AccountRepository;
import br.com.bbex.transactions.repository.OperationTypeRepository;
import br.com.bbex.transactions.repository.TransactionRepository;
import br.com.bbex.transactions.service.TransactionService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TransactionTests {

    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

    @Autowired
    private TransactionCrontroller transactionController;

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
	private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Test
	public void contextLoads() {
		assertThat(transactionController).isNotNull();
		assertThat(transactionService).isNotNull();
		assertThat(transactionRepository).isNotNull();
	}

    @Test
	public void createTransactionEndpoint() throws Exception {
        Account save = accountRepository.save(new Account("454545"));
		TransactionRequest request = new TransactionRequest(save.getAccount_id(), 4, 123.45);
        	
		this.mockMvc.perform(MockMvcRequestBuilders
			.post("/api/transactions")
			.content(objectMapper.writeValueAsString(request))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.account.document_number").value(save.getDocument_number()));
	}

    @Test
	public void listTransactionEndpoint() throws Exception {
        Account account = accountRepository.save(new Account("88888"));
		Transaction transactionNew = new Transaction(123.45);
        transactionNew.setAccount(account);
        transactionNew.setOperationType(operationTypeRepository.findById(4L).get());
        Transaction savedTransaction = transactionRepository.save(transactionNew);
        	
		this.mockMvc.perform(MockMvcRequestBuilders
			.get("/api/transactions/{transactionId}", savedTransaction.getTransaction_id()))
			.andDo(print())
			.andExpect(status().isOk());
	}

    @Test
	public void listInexistentTransactionEndpoint() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
			.get("/api/transactions/{transactionId}", 5))
			.andDo(print())
			.andExpect(status().isNotFound());
	}
}
