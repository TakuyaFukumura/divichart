package click.divichart.controller;

import click.divichart.entity.DividendHistory;
import click.divichart.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;

@Slf4j
@Controller
@RequestMapping("/list")
public class ListController {

    @Autowired
    ListService service;

    @GetMapping
    public String index(Model model, Pageable pageable) {
        log.debug("配当履歴一覧画面表示");
        Page<DividendHistory> dividendHistoryPage = service.getDividendHistory(pageable);
        model.addAttribute("dividendHistoryPage", dividendHistoryPage);
        model.addAttribute("dividendHistories", dividendHistoryPage.getContent());
        return "list";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("tickerSymbol") String tickerSymbol,
                         @ModelAttribute("amountReceived") BigDecimal amountReceived,
                         @ModelAttribute("receiptDate") Date receiptDate) {
        log.debug("配当履歴登録");
        service.insertDividendHistory(tickerSymbol, amountReceived, receiptDate);
        return "redirect:/list";
    }

    @PostMapping("/csvInsert")
    public String insert(@ModelAttribute("csvFile") MultipartFile csvFile) {
        log.debug("配当履歴CSV一括登録");
        service.csvInsert(csvFile);
        return "redirect:/list";
    }
}
