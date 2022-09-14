package cat.customize.datepicker.calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import cat.customize.R;
import cat.customize.datepicker.calendar.data.CalendarDate;
import cat.customize.ulite.DateUtils;

/**
 * Created by HSL
 * on 2022/9/6.
 */

public class CtCalendarFragment extends Fragment {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String CHOICE_MODE_SINGLE = "choice_mode_single";
    private boolean isChoiceModelSingle;
    private int mYear;
    private int mMonth;
    private GridView mGridView;
    private OnDateClickListener onDateClickListener;
    private OnDateCancelListener onDateCancelListener;
    private CtCalendarGridViewAdapter adapter;

    public static CtCalendarFragment newInstance(int year, int month) {
        CtCalendarFragment fragment = new CtCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(YEAR, year);
        args.putInt(MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    public static CtCalendarFragment newInstance(int year, int month, boolean isChoiceModelSingle) {
        CtCalendarFragment fragment = new CtCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(YEAR, year);
        args.putInt(MONTH, month);
        args.putBoolean(CHOICE_MODE_SINGLE, isChoiceModelSingle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDateClickListener = (OnDateClickListener) context;
            if(!isChoiceModelSingle){
                //多选
                onDateCancelListener = (OnDateCancelListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnDateClickListener or OnDateCancelListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYear = getArguments().getInt(YEAR);
            mMonth = getArguments().getInt(MONTH);
            isChoiceModelSingle = getArguments().getBoolean(CHOICE_MODE_SINGLE, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ct_calendar_layout, container, false);
        mGridView = (GridView) view.findViewById(R.id.ct_gv_calendar);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<CalendarDate> mListDataCalendar;//日历数据
        mListDataCalendar = CalendarDateController.getCalendarDate(mYear, mMonth);
        adapter = new CtCalendarGridViewAdapter(getActivity(), mListDataCalendar);
        mGridView.setAdapter(adapter);
        final List<CalendarDate> finalMListDataCalendar = mListDataCalendar;
        if (isChoiceModelSingle) {
            mGridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        } else {
            mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        }
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<CalendarDate> listData = ((CtCalendarGridViewAdapter) mGridView.getAdapter()).getListData();
                CalendarDate calendarDate = listData.get(position);
                if (finalMListDataCalendar.get(position).isInThisMonth()) {
                    for (int i = 0; i < listData.size(); i++) {
                        if(i==position){
                            calendarDate.setIsSelect(!calendarDate.isSelect());
                            mGridView.setItemChecked(position, true);
                            onDateClickListener.onDateClick(calendarDate);
                        }else {
                            calendarDate.setIsSelect(false);
                        }
                    }
                } else {
                    mGridView.setItemChecked(position, false);
                    onDateCancelListener.onDateCancel(calendarDate);
                }

//                if (isChoiceModelSingle) {
//                    //单选
//                    if (finalMListDataCalendar.get(position).isInThisMonth()) {
//                        calendarDate.setIsSelect(!calendarDate.isSelect());
//                        mGridView.setItemChecked(position, true);
//                        onDateClickListener.onDateClick(calendarDate);
//                    } else {
//                        mGridView.setItemChecked(position, false);
//                        onDateCancelListener.onDateCancel(calendarDate);
//                    }
//                } else {
//                    //多选
//                    if (finalMListDataCalendar.get(position).isInThisMonth()) {
//                        // mGridView.getCheckedItemIds()
//                        if(!mGridView.isItemChecked(position)){
//                            calendarDate.setIsSelect(false);
//                            onDateCancelListener.onDateCancel(calendarDate);
//                        } else {
//                            calendarDate.setIsSelect(true);
//                            onDateClickListener.onDateClick(calendarDate);
//                        }
//                    } else {
//                        mGridView.setItemChecked(position, false);
//                    }
//                }
                ((CtCalendarGridViewAdapter) mGridView.getAdapter()).notifyDataSetChanged();
            }
        });
        mGridView.post(new Runnable() {
            @Override
            public void run() {
                //需要默认选中当天
                List<CalendarDate> mListData = ((CtCalendarGridViewAdapter) mGridView.getAdapter()).getListData();
                int count = mListData.size();
                for (int i = 0; i < count; i++) {
                    if (mListData.get(i).getSolar().solarDay == cat.customize.ulite.DateUtils.getDay()
                            && mListData.get(i).getSolar().solarMonth == cat.customize.ulite.DateUtils.getMonth()
                            && mListData.get(i).getSolar().solarYear == DateUtils.getYear()) {
                        if (null != mGridView.getChildAt(i) && mListData.get(i).isInThisMonth()) {
//                             mListData.get(i).setIsSelect(true);
                            onDateClickListener.onDateClick(mListData.get(i));
                            mGridView.setItemChecked(i, true);
                        }
                    }
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (null != mGridView) {
                // mGridView.setItemChecked(mCurrentPosition, false);
                mGridView.clearChoices();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnDateClickListener {
        void onDateClick(CalendarDate calendarDate);
    }
    public interface OnDateCancelListener {
        void onDateCancel(CalendarDate calendarDate);
    }
}
