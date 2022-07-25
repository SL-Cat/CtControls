package cat.customize.bean;

/**
 * Created by HSL on 2022/7/25.
 */

public class PopuStrBean {

    private String name;
    private int id;
    private boolean isSelect = false;

    public PopuStrBean() {
    }

    public PopuStrBean(String name, int id) {
        this.name = name;
        this.id = id;
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
