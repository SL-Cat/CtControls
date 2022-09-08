package cat.customize.datepicker.calendar.data;

/**
 * Created by HSL
 * on 2022/9/6.
 */

public class Solar {
    public int solarDay;
    public int solarMonth;
    public int solarYear;
    public boolean isSFestival;
    public String solarFestivalName;//公历节日
    public String solar24Term;//24节气

    @Override
    public String toString() {
        return "Solar [solarDay=" + solarDay + ", solarMonth=" + solarMonth
                + ", solarYear=" + solarYear + ", isSFestival=" + isSFestival
                + ", solarFestivalName=" + solarFestivalName + ", solar24Term="
                + solar24Term + "]";
    }

}
