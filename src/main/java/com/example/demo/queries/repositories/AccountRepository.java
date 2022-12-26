package com.example.demo.queries.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.queries.entities.Account;

public interface AccountRepository extends JpaRepository<Account,String> {

}