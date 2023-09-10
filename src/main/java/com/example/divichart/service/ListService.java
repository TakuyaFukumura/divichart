package com.example.divichart.service;

import com.example.divichart.entity.DividendHistory;
import com.example.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ListService {

    @Autowired
    DividendHistoryRepository repository;

    public List<DividendHistory> getAllDividendHistory(){
        return repository.findAll();
    }

    public DividendHistory insertDividendHistory(BigDecimal amountReceived,
                                                 Date receiptDate){
        DividendHistory dividendHistory =
                new DividendHistory(amountReceived, receiptDate);
        System.out.println(dividendHistory.getAmountReceived()); // ここまでは小数点できてる
        return repository.save(dividendHistory);
    }

}
