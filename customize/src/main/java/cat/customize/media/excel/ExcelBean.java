package cat.customize.media.excel;

import java.util.List;

/**
 * Created by HKR on 2018/7/19.
 */

public class ExcelBean {
    private String excelName;
    private List<RowData> datas;

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public List<RowData> getDatas() {
        return datas;
    }

    public void setDatas(List<RowData> datas) {
        this.datas = datas;
    }

    public static class RowData {
        private List<String> cols;

        public List<String> getCols() {
            return cols;
        }

        public void setCols(List<String> cols) {
            this.cols = cols;
        }
    }
}
