package click.divichart.controller;

import click.divichart.bean.dto.MonthlyDividendDto;
import click.divichart.bean.form.MonthlyDividendForm;
import click.divichart.service.MonthlyDividendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

/**
 * 月別配当グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/monthlyDividend")
public class MonthlyDividendController {

    private final MonthlyDividendService service;

    @Autowired
    public MonthlyDividendController(MonthlyDividendService monthlyDividendService) {
        this.service = monthlyDividendService;
    }

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, MonthlyDividendForm monthlyDividendForm,
                        @AuthenticationPrincipal UserDetails user) {
        log.debug("月別配当グラフ表示");

        int targetYear = service.getTargetYear(monthlyDividendForm.getTargetYear());
        String chartData = service.getChartData(targetYear, user.getUsername());
        List<Integer> pastYears = service.getPastYears(5);

        MonthlyDividendDto monthlyDividendDto = new MonthlyDividendDto(
                pastYears.stream().map(String::valueOf).sorted(Comparator.reverseOrder()).toList(), // 逆順で文字列化
                String.valueOf(targetYear),
                chartData
        );
        model.addAttribute("monthlyDividendDto", monthlyDividendDto);

        return "monthlyDividend";
    }
}
