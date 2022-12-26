package com.example.demo.queries.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.example.demo.api.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Account {
	@Id
	private String id;
	private double balance;
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
}
