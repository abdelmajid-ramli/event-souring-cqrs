package com.example.demo.queries.service;

import javax.transaction.Transactional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import com.example.demo.api.events.AccountCreatedEvent;
import com.example.demo.queries.entities.Account;
import com.example.demo.queries.repositories.AccountRepository;
import com.example.demo.queries.repositories.OperationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AccountServiceHandler {
	private AccountRepository accountRepository;
	private OperationRepository operationRepository;
	
	@EventHandler
	public void on(AccountCreatedEvent event) {
		System.out.println("***********************");
		System.out.println("account created");
		Account account=new Account();
		
		account.setId(event.getId());
		account.setBalance(event.getBalance());
		account.setStatus(event.getStatus());
		
		accountRepository.save(account);
		
	}

}
