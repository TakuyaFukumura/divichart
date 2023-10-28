package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 月別配当グラフ画面用フォーム
 */
@Getter
@Setter
public class BarChartForm implements Serializable {
    private String targetYear; // 表示対象年

    BarChartForm() {
        this.targetYear = "";
    }
}
