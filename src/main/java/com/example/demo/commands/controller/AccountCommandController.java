package com.example.demo.commands.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.commands.CreateAccountCommand;
import com.example.demo.api.commands.DebitAccountCommand;
import com.example.demo.api.dto.CreateAccountRequestDTO;
import com.example.demo.api.dto.DebitAccountRequestDTO;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AccountCommandController {
	
	private CommandGateway commandGateway;
	private EventStore eventStore;
		
	@PostMapping(path = "/accounts/create")
	public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO account) {
		CreateAccountCommand command=new CreateAccountCommand(UUID.randomUUID().toString(), account.getAmount(), account.getCurrency());
		CompletableFuture<String> commandSent = commandGateway.send(command);
		return commandSent;
	}

	@PutMapping(path = "/accounts/debit")
	public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO account) {
		DebitAccountCommand command=new DebitAccountCommand(UUID.randomUUID().toString(), account.getAmount(), account.getCurrency());
		CompletableFuture<String> commandSent = commandGateway.send(command);
		return commandSent;
	}
	
	@GetMapping(path = "/accountEvents/{id}")
	public Stream eventsByAccountId(@PathVariable String id) {
		return eventStore.readEvents(id).asStream();
	}

}
