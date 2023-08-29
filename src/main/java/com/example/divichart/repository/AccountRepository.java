package com.example.divichart.repository;

import com.example.divichart.entity.Account;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface  AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findByName(String name);
}
