package cat.customize.datepicker.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cat.customize.ulite.DateUtils;

/**
 * Created by HSL
 * on 2022/9/6.
 */

public class CtCalendarViewPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM_ITEMS = 200;
    public static final int NUM_ITEMS_CURRENT = NUM_ITEMS / 2;
    private int mThisMonthPosition = DateUtils.getYear() * 12 + DateUtils.getMonth() - 1;//---100 -position
    private int number = mThisMonthPosition - NUM_ITEMS_CURRENT;
    private boolean isChoiceModelSingle;
    private boolean isCalendarType = true; //默认显示的是公历

    public CtCalendarViewPagerAdapter(FragmentManager fm, boolean isChoiceModelSingle) {
        super(fm);
        this.isChoiceModelSingle = isChoiceModelSingle;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (isCalendarType) {
            int year = getYearByPosition(position);
            int month = getMonthByPosition(position);
            fragment = CtCalendarFragment.newInstance(year, month, isChoiceModelSingle);
        }else {
            int year = getYearByPosition(position);
            int month = getMonthByPosition(position);
            fragment = CtCalendarFragment.newInstance(year, month, isChoiceModelSingle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    public int getYearByPosition(int position) {
        int year = (position + number) / 12;
        return year;
    }

    public int getMonthByPosition(int position) {
        int month = (position + number) % 12 + 1;
        return month;
    }
}
