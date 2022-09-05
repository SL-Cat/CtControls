package cat.hucustomize;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.bean.PopuStrBean;
import cat.customize.view.ISelectButton;
import cat.customize.view.PopuSpringView;
import cat.customize.view.PopupWindownView;
import cat.customize.view.ReadCleanPowerButton;
import cat.customize.view.RfidCleanButton;
import cat.customize.view.ScanResetReadButton;
import cat.customize.view.SettingPowerBottomDialog;
import cat.customize.xlist.BaseListAdapter;
import cat.hucustomize.frg.WaveRfidFragment;
import cat.hucustomize.list.RecyerActivity;
import cat.hucustomize.list.XListViewActivity;

public class MainActivity extends AppCompatActivity {

    private MainActivity instance;
    private LinearLayout frags;
    private TextView popuTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        frags = ((LinearLayout) findViewById(R.id.ct_main_more_fg));

        initView();
        popuSring();
        readBtn();
        selectBtn();
        midIgBtn();
        moreView();
        popuWind();
        findViewById(R.id.ct_to_last_frg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void midIgBtn() {
        final ScanResetReadButton readButton = (ScanResetReadButton) findViewById(R.id.ct_scan_reset_read_btn);
        readButton.setOnScanResetReadListener(new ScanResetReadButton.OnScanResetReadListener() {
            @Override
            public void scanButton() {
                Log.d("myDemo", "scanButton: ");
            }

            @Override
            public void readOrStop(boolean isFalg) {
                Log.d("myDemo", "readOrStop: " + isFalg);
            }

            @Override
            public void midImage() {
                Log.d("myDemo", "midImage: ");
            }

            @Override
            public void readIng() {
                Log.d("myDemo", "readIng: ");
            }
        });
        findViewById(R.id.ct_hide_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readButton.hideOrOpenReadBtn(false);
                readButton.hideOrOpenScanBtn(false);
            }
        });
        findViewById(R.id.ct_hide_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readButton.hideOrOpenReadBtn(true);
            }
        });
        findViewById(R.id.ct_hide_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readButton.visibilityLeft(false);
            }
        });
    }

    private void popuWind() {
        popuTv = (TextView) findViewById(R.id.ct_main_wave_popu);
        popuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindownView popupWindownView = new PopupWindownView(MainActivity.this);
                popupWindownView.showPop(popuTv, R.layout.ct_popu_layout, 0, 0, new PopupWindownView.PopupCallBack() {
                    @Override
                    public void onCallBack(PopupWindow mPopupWindow) {
                    }
                });
            }
        });

    }

    private boolean isShow = false;

    private void moreView() {
        ((TextView) findViewById(R.id.ct_main_wave_rfid_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = !isShow;
                if (isShow) {
                    findViewById(R.id.ct_main_more_fg).setVisibility(View.VISIBLE);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.ct_main_more_fg, new WaveRfidFragment());
                    fragmentTransaction.commit();
                } else {
                    findViewById(R.id.ct_main_more_fg).setVisibility(View.GONE);
                }
            }
        });
    }

    private void selectBtn() {
        final ISelectButton selectButton = (ISelectButton) findViewById(R.id.ct_main_select_btn);
        selectButton.setChooesOnClickListener(new ISelectButton.IChooesOnClickListener() {
            @Override
            public void onClickChooesItemListerenr(int position) {
                selectButton.selectItem(position);
            }
        });
    }

    private void initView() {
        ((TextView) findViewById(R.id.ct_main_touchcallbackrecycler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(instance, RecyerActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.ct_main_xlistview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(instance, XListViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readBtn() {
        ((RfidCleanButton) findViewById(R.id.ct_main_read_clean_btn)).setOnRfidBtnListener(new RfidCleanButton.OnRfidBtnListener() {
            @Override
            public void startRead(boolean isRead) {
                ToastUlit.Toast(instance, String.valueOf(isRead));
            }

            @Override
            public void clean() {
                ToastUlit.Toast(instance, "清除");
            }
        });

        ReadCleanPowerButton readbtn = (ReadCleanPowerButton) findViewById(R.id.ct_main_read_btn);
        readbtn.setPowerCode(10);
        readbtn.setOnReadCleanPowerListener(new ReadCleanPowerButton.OnReadCleanPowerListener() {
            @Override
            public void readOrStop(boolean scanFlag) {
            }

            @Override
            public void resetData() {

            }

            @Override
            public void setPower(int power) {
                SettingPowerBottomDialog dialog = new SettingPowerBottomDialog(instance, 25);
                dialog.setGravity(Gravity.BOTTOM);
                dialog.setBigByScreenWidth(1);
                dialog.setPowerSettingListener(new SettingPowerBottomDialog.OnPowerSettingListener() {
                    @Override
                    public void powerResult(int power) {
                        ToastUlit.Toast(instance, power + "");
                    }
                });
                dialog.show();

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

//两种方法，这种直接设置数据用默认的adapter ，或者换自定义的adapter
        springView.setData(mList, new PopuSpringView.OnPopuSpringListener() {
            @Override
            public void OnClickItem(int id) {
                ToastUlit.Toast(instance, id + "");
            }
        });
//        final List<TestStrBean> list = new ArrayList<>();
//        list.add(new TestStrBean("a"));
//        list.add(new TestStrBean("b"));
//        list.add(new TestStrBean("c"));
//        list.add(new TestStrBean("d"));
//        list.add(new TestStrBean("e"));
//        springView.setAdapter(new TestAdapter(instance, list), new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TestStrBean testStrBean = list.get(position);
//                ToastUlit.Toast(instance, testStrBean.getName());
//                springView.dismiss();
//            }
//        });
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
