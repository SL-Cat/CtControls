package cat.customize.datepicker.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cat.customize.R;
import cat.customize.datepicker.calendar.data.CalendarDate;
import cat.customize.xlist.BaseListAdapter;

/**
 * Created by HSL
 * on 2022/9/6.
 */

public class CtCalendarGridViewAdapter extends BaseListAdapter<CalendarDate> {

    public CtCalendarGridViewAdapter(Context context, List<CalendarDate> list) {
        super(context, list);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.ct_calendar_item,null);
        }
        TextView tv_day = (TextView) convertView.findViewById(R.id.tv_day);
        TextView tv_lunar_day = (TextView) convertView.findViewById(R.id.tv_lunar_day);
        CalendarDate calendarDate = list.get(position);

        tv_day.setText(calendarDate.getSolar().solarDay+"");
        String str;

        if(!TextUtils.isEmpty(calendarDate.getSolar().solar24Term)){
            str =  calendarDate.getSolar().solar24Term;
        }else if(!TextUtils.isEmpty(calendarDate.getSolar().solarFestivalName)){
            str = calendarDate.getSolar().solarFestivalName;
        }else{
            str = calendarDate.getLunar().getChinaDayString(calendarDate.getLunar().lunarDay);
        }
        tv_lunar_day.setText(str);
        if(calendarDate.isInThisMonth()){
            tv_day.setTextColor(mContext.getResources().getColor(R.color.color_000000));
            tv_lunar_day.setTextColor(mContext.getResources().getColor(R.color.color_000000));
            if(calendarDate.isSelect()){
                tv_day.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
                tv_day.setBackgroundResource(R.drawable.ct_radius_yellow_bg);
            }else {
                tv_day.setTextColor(mContext.getResources().getColor(R.color.color_000000));
                tv_day.setBackgroundResource(R.drawable.ct_radius_white_bg);
            }
        }else{
            tv_day.setTextColor(mContext.getResources().getColor(R.color.color_9e9e9e));
            tv_lunar_day.setTextColor(mContext.getResources().getColor(R.color.color_9e9e9e));
            tv_day.setBackgroundResource(R.drawable.ct_radius_white_bg);
        }
        return convertView;
    }

    public List<CalendarDate> getListData(){
        return list;
    }
}
