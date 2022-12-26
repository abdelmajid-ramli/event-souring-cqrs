package com.example.demo.queries.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.queries.entities.AccountOperation;

public interface OperationRepository extends JpaRepository<AccountOperation, Long> {
	//List<AccountOperation> findByAccountId(String accountId);
}
