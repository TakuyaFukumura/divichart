package click.divichart.controller;

import click.divichart.bean.entity.DividendHistory;
import click.divichart.bean.form.dividendHistoryList.BulkInsertForm;
import click.divichart.bean.form.dividendHistoryList.InsertForm;
import click.divichart.service.DividendHistoryListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 配当履歴一覧用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/dividendHistoryList")
public class DividendHistoryListController {

    @Autowired
    DividendHistoryListService service;

    /**
     * 指定ページの配当履歴一覧情報を取得してViewに渡す
     *
     * @param pageable ページ情報
     */
    @GetMapping
    public String index(Model model, Pageable pageable) {
        log.debug("配当履歴一覧画面表示");
        Page<DividendHistory> dividendHistoryPage = service.getDividendHistoryPage(pageable);
        model.addAttribute("dividendHistoryPage", dividendHistoryPage);
        model.addAttribute("dividendHistories", dividendHistoryPage.getContent());
        return "dividendHistoryList";
    }

    /**
     * 配当履歴を追加して一覧を表示
     *
     * @param insertForm 配当履歴情報
     * @return 一覧画面へリダイレクト
     */
    @PostMapping("/insert")
    public String insert(InsertForm insertForm) {
        log.debug("配当履歴登録");
        service.insertDividendHistory(
                insertForm.getTickerSymbol(),
                insertForm.getAmountReceived(),
                insertForm.getReceiptDate()
        );
        return "redirect:/dividendHistoryList";
    }

    /**
     * 配当履歴を一括登録して一覧表示
     *
     * @param bulkInsertForm 配当履歴情報
     * @return 一覧画面へリダイレクト
     */
    @PostMapping("/bulkInsert")
    public String bulkInsert(BulkInsertForm bulkInsertForm) {
        log.debug("配当履歴CSV一括登録");
        service.bulkInsert(
                bulkInsertForm.getCsvFile()
        );
        return "redirect:/dividendHistoryList";
    }
}
