package click.divichart.bean.form;

import lombok.Data;

@Data
public class LineChartForm {
    private String targetYear; // 表示対象年

    LineChartForm(){
        this.targetYear = "";
    }
}
