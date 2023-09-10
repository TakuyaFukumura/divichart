package com.example.divichart.repository;

import com.example.divichart.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DividendHistoryRepository extends CrudRepository<Account, Long> {
    // 後で書く
}
