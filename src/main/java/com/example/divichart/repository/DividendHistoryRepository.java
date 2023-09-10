package com.example.divichart.repository;

import com.example.divichart.entity.DividendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DividendHistoryRepository extends JpaRepository<DividendHistory, Long> {
    // 後で書く
}
