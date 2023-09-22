package cat.customize.media;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cat.customize.media.excel.ExcelTitleBean;
import cat.customize.media.excel.ExcelUtils;
import cat.customize.ulite.DateUtils;

/**
 * Created by HSL
 * on 2023/4/10.
 */

public class ExportHelper<T> {

    private static ExportHelper instance;
    private Activity context;
    private List<ExcelTitleBean> title = new ArrayList<>();
    private String path = null; //自定义文件路径

    private ExportHelper(Activity context) {
        this.context = context;

    }

    public static ExportHelper getInstance(Activity context) {
        synchronized (ExportHelper.class) {
            if (instance == null) {
                instance = new ExportHelper(context);
            }
        }
        return instance;
    }

    public interface OnExportListener {
        void exportSuccess();

        void exportError(String s);
    }

    /**
     * 设置横向标题栏
     *
     * @param title
     */
    public void initExport(List<ExcelTitleBean> title, String path) {
        this.title = title;
        this.path = path;
    }


    public void exportWord(List<T> exportBeanList, OnExportListener onExportListener) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //未授权，则加入待授权的权限集合中
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            exportInfo(null, exportBeanList, onExportListener);
        }
    }


    public void exportWord(String wordName, List<T> exportBeanList, OnExportListener onExportListener) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //未授权，则加入待授权的权限集合中
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            exportInfo(wordName, exportBeanList, onExportListener);
        }
    }

    private void exportInfo(String wordName, List<T> exportBeanList, OnExportListener onExportListener) {
        if (ExcelUtils.getSDPath() != null && !ExcelUtils.getSDPath().isEmpty()) {
            String pathname = null;
            if (path != null) {
                pathname = ExcelUtils.getSDPath() + path;
            } else {
                pathname = ExcelUtils.getSDPath() + "/export/";
            }

            File file = new File(pathname);
            ExcelUtils.makeDir(file);
            String fileName = "-";
            if (wordName != null) {
                fileName = file.toString() + "/" + wordName + ".xls";
            } else {
                fileName = file.toString() + "/" + DateUtils.getCurrentDate() + ".xls";
            }
            ExcelUtils.initExcel(fileName, title);
            try {
                ExcelUtils.writeObjListToExcel(getBillData(exportBeanList), fileName, context);
                if (onExportListener != null)
                    onExportListener.exportSuccess();
            } catch (Exception e) {
                if (onExportListener != null)
                    onExportListener.exportError(e.toString());
            }
        } else {
            onExportListener.exportError("e.toString()");
        }
    }


    private ArrayList<ArrayList<String>> getBillData(List<T> exportBean) {
        ArrayList<ArrayList<String>> bill2List = new ArrayList<>();
        for (T iterator : exportBean) {
            Field[] fields = iterator.getClass().getDeclaredFields();
            ArrayList<String> beanList = new ArrayList<>();
            for (Field field : fields) {
                for (ExcelTitleBean excelTitleBean : title) {
                    if (excelTitleBean.getRowCode().equals(field.getName())) {
                        String byFieldName = getFieldValueByFieldName(field.getName(), iterator);
                        beanList.add(byFieldName);
                        break;
                    }
                }
            }
            bill2List.add(beanList);
        }
        return bill2List;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }
}
