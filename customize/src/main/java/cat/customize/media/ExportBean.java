package cat.customize.media;

/**
 * Created by HSL
 * on 2023/4/10.
 */

public class ExportBean {

    private String epc;
    private String barcode;
    private String businessCode;
    private int operQty;

    public ExportBean(String epc, String barcode, String businessCode,int operQty) {
        this.epc = epc;
        this.barcode = barcode;
        this.businessCode = businessCode;
        this.operQty = operQty;
    }

    public int getOperQty() {
        return operQty;
    }

    public void setOperQty(int operQty) {
        this.operQty = operQty;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

}
