package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 配当ポートフォリオ画面用フォーム
 */
@Getter
@Setter
public class DividendPortfolioForm implements Serializable {
    private String targetYear; // 表示対象年

    DividendPortfolioForm() {
        this.targetYear = "";
    }
}
