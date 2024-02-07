package click.divichart.controller;

import click.divichart.bean.entity.Users;
import click.divichart.bean.form.CreateUserAccountForm;
import click.divichart.service.CreateUserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ユーザアカウント作成用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/createUserAccount")
public class CreateUserAccountController {

    private final CreateUserAccountService service;

    @Autowired
    public CreateUserAccountController(CreateUserAccountService service) {
        this.service = service;
    }

    /**
     * ユーザ作成画面表示
     */
    @GetMapping
    public String index() {
        log.debug("アカウント作成画面表示");
        return "createUserAccount";
    }

    /**
     * ユーザアカウント作成
     *
     * @param createUserAccountForm ユーザ情報
     * @return ログイン画面へリダイレクト
     */
    @PostMapping("/create")
    public String create(CreateUserAccountForm createUserAccountForm, Model model) {
        try {
            service.create(
                    createUserAccountForm.getUsername(),
                    createUserAccountForm.getPassword()
            );
        } catch (DataAccessException e) {
            model.addAttribute("errorMessage", "アカウント作成に失敗しました");
            return "createUserAccount";
        }
        return "redirect:/login";
    }
}
