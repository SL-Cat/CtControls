package cat.customize.datepicker.calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.customize.R;

/**
 * Created by HSL
 * on 2022/9/6.
 */

public class CtCalendarPagerFragment extends Fragment {

    private static final String CHOICE_MODE_SINGLE="choice_mode_single";
    private boolean isChoiceModelSingle;
    private ViewPager viewPager;
    private OnPageChangeListener onPageChangeListener;


    public static CtCalendarPagerFragment newInstance(boolean isChoiceModelSingle) {
        CtCalendarPagerFragment fragment = new CtCalendarPagerFragment();
        Bundle args = new Bundle();
        args.putBoolean(CHOICE_MODE_SINGLE, isChoiceModelSingle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onPageChangeListener = (OnPageChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnDateClickListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChoiceModelSingle = getArguments().getBoolean(CHOICE_MODE_SINGLE,false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ct_calendar_pager_layout, container, false);
        initViewPager(view);
        return view;
    }

    private void initViewPager(View view){
        viewPager = (ViewPager) view.findViewById(R.id.ct_calendar_pager);
        viewPager.setOffscreenPageLimit(2);
        final CtCalendarViewPagerAdapter myAdapter = new CtCalendarViewPagerAdapter(getChildFragmentManager(),isChoiceModelSingle);
        viewPager.setAdapter(myAdapter);
        viewPager.setCurrentItem(CtCalendarViewPagerAdapter.NUM_ITEMS_CURRENT);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int year = myAdapter.getYearByPosition(position);
                int month = myAdapter.getMonthByPosition(position);
                // tv_date.setText(year+"-"+month+"");
                onPageChangeListener.onPageChange(year,month);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }



    public interface OnPageChangeListener {
        void onPageChange(int year, int month);
    }
}
