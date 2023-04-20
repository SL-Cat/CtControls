package cat.hucustomize;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cat.customize.ulite.system.AndroidUtils;
import cat.hucustomize.frg.BannerFragment;
import cat.hucustomize.frg.ButtonFragment;
import cat.hucustomize.frg.DialogFragment;
import cat.hucustomize.frg.SelectFragment;
import cat.hucustomize.list.RecyclerFragment;
import cat.hucustomize.list.XListViewFragment;

import static cat.hucustomize.ThreeActivity.createHelper;

/**
 * Created by HSL
 * on 2022/11/10.
 */

public class MainFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_main_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.ct_main_touchcallbackrecycler).setOnClickListener(this);
        view.findViewById(R.id.ct_main_xlistview).setOnClickListener(this);
        view.findViewById(R.id.ct_main_btn).setOnClickListener(this);
        view.findViewById(R.id.ct_main_select_btn).setOnClickListener(this);
        view.findViewById(R.id.ct_main_dialog_btn).setOnClickListener(this);
        view.findViewById(R.id.ct_main_lead_read).setOnClickListener(this);
        view.findViewById(R.id.ct_main_lead_other).setOnClickListener(this);
        view.findViewById(R.id.ct_main_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.ct_main_socket).setOnClickListener(this);
        view.findViewById(R.id.ct_main_other).setOnClickListener(this);
        view.findViewById(R.id.ct_main_banner).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ct_main_touchcallbackrecycler:
                createHelper.addFragment(new RecyclerFragment());
                break;
            case R.id.ct_main_xlistview:
                createHelper.addFragment(new XListViewFragment());
                break;
            case R.id.ct_main_banner:
                createHelper.addFragment(new BannerFragment());
                break;
            case R.id.ct_main_btn:
                createHelper.addFragment(new ButtonFragment());
                break;
            case R.id.ct_main_select_btn:
                createHelper.addFragment(new SelectFragment());
                break;
            case R.id.ct_main_dialog_btn:
                createHelper.addFragment(new DialogFragment());
                break;
            case R.id.ct_main_lead_read:
                break;
            case R.id.ct_main_lead_other:
                break;
            case R.id.ct_main_bluetooth:
                break;
            case R.id.ct_main_socket:
                AndroidUtils.startPackage(getActivity(),"com.redstone.ota.ui");
                break;
            case R.id.ct_main_other:
                AndroidUtils.startPackage(getActivity(),"com.seuic.uhftool");
                break;
        }
    }

}
