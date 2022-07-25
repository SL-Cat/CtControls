package cat.hucustomize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cat.customize.bean.PopuStrBean;
import cat.customize.view.PopuSpringView;

public class MainActivity extends AppCompatActivity {

    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        popuSring();
    }

    private void popuSring() {
        final List<PopuStrBean> mList = new ArrayList<>();
        mList.add(new PopuStrBean("code01", 1));
        mList.add(new PopuStrBean("code02", 2));
        mList.add(new PopuStrBean("code03", 3));
        mList.add(new PopuStrBean("code04", 4));
        PopuSpringView springView = (PopuSpringView) findViewById(R.id.ct_main_popu_spring);
        mList.get(1).setSelect(true);
        springView.setSelectTextColor(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
        springView.setData(mList, new PopuSpringView.OnPopuSpringListener() {
            @Override
            public void OnClickItem(int id) {
                ToastUlit.Toast(instance, id + "");
            }
        });
    }
}
