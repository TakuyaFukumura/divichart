package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 配当達成率グラフ画面用フォーム
 */
@Getter
@Setter
public class DividendAchievementRateForm implements Serializable {
    private String goalDividendAmount; // 目標配当

    DividendAchievementRateForm() {
        this.goalDividendAmount = "";
    }
}
