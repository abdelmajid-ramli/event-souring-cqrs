package com.example.demo.api.events;

import com.example.demo.api.enums.AccountStatus;

import lombok.Getter;

public class AccountActivatedEvent extends BaseEvent<String> {
	@Getter
	private final AccountStatus status;

	public AccountActivatedEvent(String id, AccountStatus status) {
		super(id);
		this.status = status;
	}
}