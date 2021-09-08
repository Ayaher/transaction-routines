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

import br.com.bbex.transactions.controller.AccountController;
import br.com.bbex.transactions.controller.request.CreditLimitRequest;
import br.com.bbex.transactions.model.Account;
import br.com.bbex.transactions.repository.AccountRepository;
import br.com.bbex.transactions.service.AccountService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class AccountTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountController accountController;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void contextLoads() {
		assertThat(accountService).isNotNull();
		assertThat(accountController).isNotNull();
		assertThat(accountRepository).isNotNull();
	}

	@Test
	public void createAccountEndpoint() throws Exception {	
		Account account = new Account("111111");		
		this.mockMvc.perform(MockMvcRequestBuilders
			.post("/api/accounts")
			.content(objectMapper.writeValueAsString(account))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.document_number").value(account.getDocument_number()));
	}

	@Test
	public void createBadAccountEndpoint() throws Exception {	
		Account account = new Account("abcde");		
		this.mockMvc.perform(MockMvcRequestBuilders
			.post("/api/accounts")
			.content(objectMapper.writeValueAsString(account))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	public void listingAccountsEndpoint() throws Exception {
		Account save = accountRepository.save(new Account("5555"));
		
		this.mockMvc.perform(get("/api/accounts/{accountId}", save.getAccount_id()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.document_number").value(save.getDocument_number()));
	}

	@Test
	public void setAccountCreditLimitEndpoint() throws Exception {	
		Account account = new Account("87878787");
		Account save = accountRepository.save(account);

		// Initial limit must be 0
		this.mockMvc.perform(get("/api/accounts/{accountId}", save.getAccount_id()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.availableCreditLimit").value(0));

		CreditLimitRequest newCreditLimit = new CreditLimitRequest(500);
		this.mockMvc.perform(MockMvcRequestBuilders
			.patch("/api/accounts/{accountId}/creditlimit", save.getAccount_id())
			.content(objectMapper.writeValueAsString(newCreditLimit))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.account_id").value(account.getAccount_id()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.document_number").value(account.getDocument_number()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.availableCreditLimit").value(newCreditLimit.getCreditLimit()));
	}

}
