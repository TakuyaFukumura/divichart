package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 累計配当グラフ画面用フォーム
 */
@Getter
@Setter
public class CumulativeDividendForm implements Serializable {
    private String targetYear; // 表示対象年

    CumulativeDividendForm() {
        this.targetYear = "";
    }
}
