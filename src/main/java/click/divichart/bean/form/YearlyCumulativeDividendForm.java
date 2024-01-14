package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 年別累計配当グラフ画面用フォーム
 */
@Getter
@Setter
public class YearlyCumulativeDividendForm implements Serializable {
    private String targetYear; // 表示対象年

    YearlyCumulativeDividendForm() {
        this.targetYear = "";
    }
}
