package com.example.divichart.controller;

import com.example.divichart.entity.DividendHistory;
import com.example.divichart.service.ListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/list")
public class ListController {

    private static final Logger log = LoggerFactory.getLogger(ListController.class);

    @Autowired
    ListService service;

    @GetMapping
    public String index(Model model) {
        log.debug("配当履歴一覧画面表示");
        List<DividendHistory> dividendHistoryList = service.getAllDividendHistory();
        model.addAttribute("dividendHistoryList", dividendHistoryList);
        return "list";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("amountReceived") BigDecimal amountReceived,
                         @ModelAttribute("receiptDate") Date receiptDate) {
        log.debug("配当履歴登録");
        service.insertDividendHistory(amountReceived, receiptDate);
        return "redirect:/list";
    }

    @PostMapping("/csvInsert")
    public String insert(@ModelAttribute("csvFile") MultipartFile csvFile) {
        log.debug("配当履歴CSV一括登録");
        service.csvInsert(csvFile);
        return "redirect:/list";
    }
}
