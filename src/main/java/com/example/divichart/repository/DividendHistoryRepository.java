package com.example.divichart.repository;

import com.example.divichart.dto.DividendDto;
import com.example.divichart.entity.DividendHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DividendHistoryRepository extends JpaRepository<DividendHistory, Long> {
    @Query(value = "SELECT COALESCE(SUM(amount_received), 0) FROM dividend_history", nativeQuery = true)
    BigDecimal getDividendSum();

    @Query(value = "SELECT COALESCE(SUM(amount_received), 0) FROM dividend_history " +
            "WHERE receipt_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    BigDecimal getDividendSum(
            @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
    );

    @Query(value = "SELECT COALESCE(ticker_symbol, ''), " +
            "COALESCE(SUM(amount_received), 0) AS amount_received " +
            "FROM dividend_history " +
            "WHERE receipt_date BETWEEN :startDate AND :endDate " +
            "GROUP BY ticker_symbol " +
            "ORDER BY amount_received DESC", nativeQuery = true)
    List<Object[]> getDividendTotalForStock(
            @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
    );
}
