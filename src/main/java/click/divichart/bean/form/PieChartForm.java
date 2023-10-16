package click.divichart.bean.form;

import lombok.Data;

@Data
public class PieChartForm {
    private String targetYear; // 表示対象年

    PieChartForm(){
        this.targetYear = "";
    }
}
