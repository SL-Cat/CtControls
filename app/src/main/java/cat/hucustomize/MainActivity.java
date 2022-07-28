package cat.hucustomize;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.bean.PopuStrBean;
import cat.customize.view.PopuSpringView;
import cat.customize.view.ReadCleanPowerButton;
import cat.customize.xlist.BaseListAdapter;

public class MainActivity extends AppCompatActivity {

    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        popuSring();
        readBtn();
    }

    private void readBtn() {
        ReadCleanPowerButton readbtn = (ReadCleanPowerButton) findViewById(R.id.ct_main_read_btn);
        readbtn.setPowerCode(10);
        readbtn.setOnReadCleanPowerListener(new ReadCleanPowerButton.OnReadCleanPowerListener() {
            @Override
            public void readOrStop(boolean scanFlag) {
                Log.d("myDemo", "readOrStop: " + scanFlag);
            }

            @Override
            public void resetData() {
                Log.d("myDemo", "resetData: ");

            }

            @Override
            public void setPower(int power) {
                Log.d("myDemo", "setPower: " + power);

            }
        });
    }

    private void popuSring() {
        final List<PopuStrBean> mList = new ArrayList<>();
        mList.add(new PopuStrBean("code01", 1));
        mList.add(new PopuStrBean("code02", 2));
        mList.add(new PopuStrBean("code03", 3));
        mList.add(new PopuStrBean("code04", 4));
        final PopuSpringView springView = (PopuSpringView) findViewById(R.id.ct_main_popu_spring);

//        springView.setData(mList, new PopuSpringView.OnPopuSpringListener() {
//            @Override
//            public void OnClickItem(int id) {
//                ToastUlit.Toast(instance, id + "");
//            }
//        });
        final List<TestStrBean> list = new ArrayList<>();
        list.add(new TestStrBean("a"));
        list.add(new TestStrBean("b"));
        list.add(new TestStrBean("c"));
        list.add(new TestStrBean("d"));
        list.add(new TestStrBean("e"));
        springView.setAdapter(new TestAdapter(instance, list), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestStrBean testStrBean = list.get(position);
                ToastUlit.Toast(instance, testStrBean.getName());
                springView.dismiss();
            }
        });
    }

    private class TestAdapter extends BaseListAdapter<TestStrBean> {

        public TestAdapter(Context context, List<TestStrBean> list) {
            super(context, list);
        }

        @Override
        public View bindView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.test_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.test_item_tv);
            tv.setText(list.get(position).getName());
            tv.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            return convertView;
        }
    }
}
