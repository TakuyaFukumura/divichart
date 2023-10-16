package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineChartForm {
    private String targetYear; // 表示対象年

    LineChartForm(){
        this.targetYear = "";
    }
}
