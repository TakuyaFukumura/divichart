package com.example.divichart.service;

import com.example.divichart.entity.Account;
import com.example.divichart.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndexService {

    @Autowired
    AccountRepository repository;

    public String getUserNameById(Long id) {

        Optional<Account> accountOptional = repository.findById(id);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return account.getName();

        } else {
            return "";
        }
    }

}
