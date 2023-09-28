package cat.hucustomize;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cat.customize.bean.PopuStrBean;
import cat.customize.dialog.CtLoadDialog;
import cat.customize.dialog.CtToastDialog;
import cat.customize.fragment.CtUiHelper;
import cat.customize.fragment.CtUiHelperListener;
import cat.customize.media.ExportHelper;
import cat.customize.media.FolderDialog;
import cat.customize.media.excel.ExcelBean;
import cat.customize.media.excel.ExcelTitleBean;
import cat.customize.ulite.system.CtLog;
import cat.hucustomize.frg.BannerFragment;
import cat.hucustomize.frg.BluetoothFragment;
import cat.hucustomize.frg.ButtonFragment;
import cat.hucustomize.frg.DialogFragment;
import cat.hucustomize.frg.LeadReadFragment;
import cat.hucustomize.frg.SelectFragment;
import cat.hucustomize.frg.TestViewpager;
import cat.hucustomize.list.XListViewFragment;
import cat.hucustomize.permission.PermissionListener;

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
                ExportHelper instance = ExportHelper.getInstance(getActivity());
                List<PopuStrBean> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    list.add(new PopuStrBean("index:232--" + i, i));
                }
                List<ExcelTitleBean> titleBeans = new ArrayList<>();
                titleBeans.add(new ExcelTitleBean("1","id"));
                titleBeans.add(new ExcelTitleBean("2","name"));
                instance.initExport(titleBeans,null);
                instance.exportWord(list, new ExportHelper.OnExportListener() {
                    @Override
                    public void exportSuccess() {
                        CtLog.d("exportSuccess");
                    }

                    @Override
                    public void exportError(String s) {
                        CtLog.d("exportError");
                    }
                });
//                createHelper.addFragment(new RecyclerFragment());
                break;
            case R.id.ct_main_xlistview:
                FolderDialog folderDialog = new FolderDialog(getActivity());
                folderDialog.startSearch(new FolderDialog.OnFolderSearchListener() {
                    @Override
                    public void onSearchOver() {
                        Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                    }
                });
                folderDialog.setOnFolderListener(new FolderDialog.OnFolderListener() {
                    @Override
                    public void onStartImport() {
                        Toast.makeText(getActivity(), "开始导入", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(List<ExcelBean.RowData> datas) {
                        Toast.makeText(getActivity(), "成功导入: "+datas.size(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError() {
                        Toast.makeText(getActivity(), "ERROR: ", Toast.LENGTH_SHORT).show();
                    }
                });
                folderDialog.show();
//                createHelper.addFragment(new XListViewFragment());
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
                createHelper.addFragment(new LeadReadFragment());
                break;
            case R.id.ct_main_lead_other:
                break;
            case R.id.ct_main_bluetooth:
                initBluetoothPermission();
                break;
            case R.id.ct_main_socket:
                break;
            case R.id.ct_main_other:
                createHelper.addFragment(new TestViewpager());
                break;
        }
    }

    public void initBluetoothPermission() {
        requestRunTimePermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}
                , new PermissionListener() {
                    @Override
                    public void onGranted() {  //所有权限授权成功
                        createHelper.addFragment(new BluetoothFragment());
                    }

                    @Override
                    public void onGranted(List<String> grantedPermission) { //授权失败权限集合

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) { //授权成功权限集合

                    }
                });
    }

    /**
     * 权限申请
     *
     * @param permissions 待申请的权限集合
     * @param listener    申请结果监听事件
     */
    protected void requestRunTimePermission(String[] permissions, PermissionListener listener) {
        //用于存放为授权的权限
        List<String> permissionList = new ArrayList<>();
        //遍历传递过来的权限集合
        for (String permission : permissions) {
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                //未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        //判断集合
        if (!permissionList.isEmpty()) {  //如果集合不为空，则需要去授权
            ActivityCompat.requestPermissions(getActivity(), permissionList.toArray(new String[permissionList.size()]), 1);
        } else {  //为空，则已经全部授权
            listener.onGranted();
        }
    }


}
