package com.example.divichart.repository;

import com.example.divichart.entity.DividendHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.sql.Date;

public interface DividendHistoryRepository extends JpaRepository<DividendHistory, Long> {
    @Query(value = "SELECT COALESCE(SUM(amount_received), 0) FROM dividend_history", nativeQuery = true)
    BigDecimal getDividendSum();

    @Query(value = "SELECT COALESCE(SUM(amount_received), 0) FROM dividend_history " +
            "WHERE receipt_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    BigDecimal getDividendSum(
            @Param("startDate") Date startDate
            ,@Param("endDate") Date endDate
    );
}
