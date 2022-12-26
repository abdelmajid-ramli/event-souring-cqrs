package com.example.demo.commands.aggregates;

import java.util.stream.Stream;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.api.commands.CreateAccountCommand;
import com.example.demo.api.commands.CreditAccountCommand;
import com.example.demo.api.commands.DebitAccountCommand;
import com.example.demo.api.enums.AccountStatus;
import com.example.demo.api.events.AccountActivatedEvent;
import com.example.demo.api.events.AccountCreatedEvent;
import com.example.demo.api.events.AccountCreditedEvent;
import com.example.demo.api.events.AccountDebitedEvent;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {
	@AggregateIdentifier
	private String accountId;
	private double balance;
	private String currency;
	private AccountStatus status;

	@CommandHandler
	public AccountAggregate(CreateAccountCommand accountCommand) {
		if (accountCommand.getBalance() < 0) {
			throw new RuntimeException("can't create account with negative balance");
		}

		AccountCreatedEvent event = new AccountCreatedEvent(accountCommand.getId(), accountCommand.getBalance(),
				accountCommand.getCurrency(),AccountStatus.CREATED);
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		this.accountId = event.getId();
		this.balance = event.getBalance();
		this.currency = event.getCurrency();
		this.status = AccountStatus.CREATED;

		AccountActivatedEvent accountActivatedEvent = new AccountActivatedEvent(event.getId(), AccountStatus.ACTIVATED);
		AggregateLifecycle.apply(accountActivatedEvent);
	}

	@EventSourcingHandler
	public void on(AccountActivatedEvent event) {
		this.status = event.getStatus();

	}

	@CommandHandler
	public void handle(DebitAccountCommand debitAccountCommand) {
		if ((this.balance < 0) || (this.balance - debitAccountCommand.getAmount()) < 0) {
			throw new RuntimeException("can't debit account");
		}
		AggregateLifecycle.apply(new AccountDebitedEvent(debitAccountCommand.getId(), debitAccountCommand.getAmount(),
				debitAccountCommand.getCurrency()));
	}

	@EventSourcingHandler
	public void on(AccountDebitedEvent accountDebitedEvent) {
		this.balance = this.balance - accountDebitedEvent.getAmount();
	}

	@CommandHandler
	public void handle(CreditAccountCommand creditAccountCommand) {
		AggregateLifecycle.apply(new AccountCreditedEvent(creditAccountCommand.getId(),
				creditAccountCommand.getAmount(), creditAccountCommand.getCurrency()));
	}

	@EventSourcingHandler
	public void on(AccountCreditedEvent accountCreditedEvent) {
		this.balance = this.balance + accountCreditedEvent.getAmount();
	}

}
