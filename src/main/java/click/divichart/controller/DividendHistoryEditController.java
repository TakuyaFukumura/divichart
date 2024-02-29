package click.divichart.controller;

import click.divichart.bean.dto.DividendHistoryDto;
import click.divichart.bean.form.DividendHistoryEditForm;
import click.divichart.service.DividendHistoryEditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 配当履歴編集用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/dividendHistoryEdit")
public class DividendHistoryEditController {

    private final DividendHistoryEditService service;

    @Autowired
    public DividendHistoryEditController(DividendHistoryEditService dividendHistoryEditService) {
        this.service = dividendHistoryEditService;
    }

    /**
     * 編集対象の配当履歴を取得してViewへ渡す
     *
     * @param id 配当履歴の主キー（ID）
     */
    @GetMapping
    public String index(Model model, @RequestParam("id") Long id) {
        log.debug("配当履歴編集画面表示");

        DividendHistoryDto dividendHistoryDto = service.getDividendHistory(id);
        model.addAttribute("dividendHistoryDto", dividendHistoryDto);

        return "dividendHistoryEdit";
    }

    /**
     * 配当履歴情報をupdateする
     *
     * @param dividendHistoryEditForm 編集後の配当履歴情報
     * @param user                    ログインユーザ情報
     * @return 配当一覧画面へリダイレクト
     */
    @PostMapping("/submit")
    public String submit(DividendHistoryEditForm dividendHistoryEditForm, @AuthenticationPrincipal UserDetails user) {
        log.debug("配当履歴編集登録画面表示");
        service.save(
                dividendHistoryEditForm.getId(),
                dividendHistoryEditForm.getTickerSymbol(),
                dividendHistoryEditForm.getAmountReceived(),
                dividendHistoryEditForm.getReceiptDate(),
                user.getUsername()
        );
        return "redirect:/dividendHistoryList";
    }
}
