package com.example.demo.api.commands;

import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String> {
	@Getter
	private double balance;
	@Getter
	private String currency;

	public CreateAccountCommand(String id, double balance, String currency) {
		super(id);
		this.balance = balance;
		this.currency = currency;
	}
}