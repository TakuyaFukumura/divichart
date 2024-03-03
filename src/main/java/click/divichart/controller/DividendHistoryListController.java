package click.divichart.controller;

import click.divichart.bean.entity.DividendHistory;
import click.divichart.bean.form.list.InsertForm;
import click.divichart.service.DividendHistoryListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 配当履歴一覧用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/dividendHistoryList")
public class DividendHistoryListController {

    private final DividendHistoryListService service;

    @Autowired
    public DividendHistoryListController(DividendHistoryListService dividendHistoryListService) {
        this.service = dividendHistoryListService;
    }

    /**
     * 指定ページの配当履歴一覧情報を取得してViewに渡す
     *
     * @param model    画面に渡す情報
     * @param pageable ページ情報
     * @param user     ログインユーザ情報
     * @return 配当履歴一覧画面
     */
    @GetMapping
    public String index(Model model, Pageable pageable, @AuthenticationPrincipal UserDetails user) {
        log.debug("配当履歴一覧画面表示");
        Page<DividendHistory> dividendHistoryPage = service.getDividendHistoryPage(user.getUsername(), pageable);
        model.addAttribute("dividendHistoryPage", dividendHistoryPage);
        model.addAttribute("dividendHistories", dividendHistoryPage.getContent());
        return "dividendHistoryList";
    }

    /**
     * 配当履歴を追加して一覧を表示
     *
     * @param insertForm 配当履歴情報
     * @param user       ログインユーザ情報
     * @return 一覧画面へリダイレクト
     */
    @PostMapping("/insert")
    public String insert(InsertForm insertForm, @AuthenticationPrincipal UserDetails user) {
        log.debug("配当履歴登録");
        service.insertDividendHistory(
                insertForm.getTickerSymbol(),
                insertForm.getAmountReceived(),
                insertForm.getReceiptDate(),
                user.getUsername()
        );
        return "redirect:/dividendHistoryList";
    }

    /**
     * 配当履歴を一括登録して一覧表示
     *
     * @param csvFile 配当情報CSV
     * @param user    ログインユーザ情報
     * @return 一覧画面へリダイレクト
     */
    @PostMapping("/bulkInsert")
    public String bulkInsert(@RequestParam("csvFile") MultipartFile csvFile, @AuthenticationPrincipal UserDetails user) {
        log.debug("配当履歴CSV一括登録");
        service.bulkInsert(csvFile, user.getUsername());
        return "redirect:/dividendHistoryList";
    }
}
