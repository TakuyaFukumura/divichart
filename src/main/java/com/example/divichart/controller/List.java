package com.example.divichart.controller;

import com.example.divichart.service.ListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( {"/list"} )
public class List {

    private static final Logger log = LoggerFactory.getLogger(List.class);

    @Autowired
    ListService service;

    @GetMapping
    public String index() {
        log.info("配当履歴一覧画面表示");
        return "list";
    }
}
