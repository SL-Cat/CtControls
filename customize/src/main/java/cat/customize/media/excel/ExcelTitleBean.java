package cat.customize.media.excel;

/**
 * Created by HSL
 * on 2023/9/21.
 */

public class ExcelTitleBean {

    /**
     * 标题名
     */
    public String rowName;

    /**
     * 对应的数据对象属性名
     */
    public String rowCode;

    public ExcelTitleBean(String rowName, String rowCode) {
        this.rowName = rowName;
        this.rowCode = rowCode;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getRowCode() {
        return rowCode;
    }

    public void setRowCode(String rowCode) {
        this.rowCode = rowCode;
    }
}
