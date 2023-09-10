package com.example.divichart.controller;

import com.example.divichart.entity.DividendHistory;
import com.example.divichart.service.ListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( {"/list"} )
public class ListController {

    private static final Logger log = LoggerFactory.getLogger(ListController.class);

    @Autowired
    ListService service;

    @GetMapping
    public String index(Model model) {
        log.info("配当履歴一覧画面表示");
        List<DividendHistory> dividendHistoryList = service.getAllDividendHistory();
        model.addAttribute("dividendHistoryList", dividendHistoryList);
        return "list";
    }
}
