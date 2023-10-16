package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PieChartForm {
    private String targetYear; // 表示対象年

    PieChartForm(){
        this.targetYear = "";
    }
}
