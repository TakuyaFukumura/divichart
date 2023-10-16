package click.divichart.bean.form;

import lombok.Data;

@Data
public class BarChartForm {
    private String targetYear; // 表示対象年

    BarChartForm(){
        this.targetYear = "";
    }
}
