package com.example.divichart.service;

import com.example.divichart.entity.DividendHistory;
import com.example.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ListService {

    @Autowired
    DividendHistoryRepository repository;

    public List<DividendHistory> getAllDividendHistory(){
        return repository.findAll();
    }

}
