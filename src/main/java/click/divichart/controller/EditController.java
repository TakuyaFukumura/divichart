package click.divichart.controller;

import click.divichart.bean.dto.DividendHistoryDto;
import click.divichart.bean.form.EditForm;
import click.divichart.service.EditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/edit")
public class EditController {

    @Autowired
    EditService service;

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

        return "edit";
    }

    /**
     * 配当履歴情報をupdateする
     *
     * @param editForm 編集後の配当履歴情報
     * @return 配当一覧画面へリダイレクト
     */
    @PostMapping("/submit")
    public String submit(EditForm editForm) {
        log.debug("配当履歴編集登録画面表示");
        service.save(
                editForm.getId(),
                editForm.getTickerSymbol(),
                editForm.getAmountReceived(),
                editForm.getReceiptDate()
        );
        return "redirect:/dividendHistoryList";
    }
}
