package cat.customize.bean;

/**
 * Created by HSL on 2022/7/25.
 */

public class PopuStrBean {

    private String name;
    private String type;
    private int id;
    private boolean isSelect = false;

    public PopuStrBean() {
    }

    public PopuStrBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
