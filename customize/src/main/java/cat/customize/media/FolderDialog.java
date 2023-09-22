package cat.customize.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cat.customize.R;
import cat.customize.dialog.BaseDialog;
import cat.customize.media.adapter.FolderDataAdapter;
import cat.customize.media.excel.ExcelBean;
import cat.customize.media.excel.ExcelUtils;
import cat.customize.media.model.FileInfo;
import cat.customize.media.utils.FileUtil;
import cat.customize.ulite.system.AndroidUtils;


/**
 * Created by HSL
 * on 2023/6/13.
 */

public class FolderDialog extends BaseDialog {

    private ListView ry;
    private FolderDataAdapter adapter;
    private Context context;
    private List<FileInfo> mList = new ArrayList<>();
    private OnFolderListener onFolderListener; //导出文件的监听
    private OnFolderSearchListener onFolderSearchListener; //查找文件的监听
    private LinearLayout viewBg;
    private TextView titleTv;

    public interface OnFolderListener {
        void onStartImport(); //开始导入

        void onSuccess(List<ExcelBean.RowData> datas); //导入成功

        void onError(); //导入失败
    }

    public interface OnFolderSearchListener {
        void onSearchOver();
    }

    public FolderDialog(@NonNull Context context) {
        super(context, R.style.ct_RadiusDialog);
        initView(context);
        this.setCancelable(false);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.ct_folder_layout, null, false);
        setContentView(dialogView);
        viewBg = (LinearLayout) dialogView.findViewById(R.id.folder_ll);
        titleTv = (TextView) dialogView.findViewById(R.id.folder_title);
        ry = ((ListView) dialogView.findViewById(R.id.folder_ry));
        TextView closeTv = ((TextView) dialogView.findViewById(R.id.folder_close_btn));
        closeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        adapter = new FolderDataAdapter(context, mList);
        ry.setAdapter(adapter);
        ry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onFolderListener != null) {
                    onFolderListener.onStartImport();
                    AndroidUtils.MainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            selectWord(position, context);
                        }
                    }, 200);
                }
            }
        });
        setBigByScreenWidthHeight(0.8f, 0.6f);
    }

    /**
     * 选择文件进行异步导出
     *
     * @param position
     * @param context
     */
    private void selectWord(int position, Context context) {
        ExcelUtils.readExcel(context, mList.get(position).getFilePath(), new ExcelUtils.OnExcelDataCallback() {
            @Override
            public void onExcelDataResult(ExcelBean excelBean) {
                AndroidUtils.MainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (excelBean.getExcelName().equals("error")) {
                            onFolderListener.onError();
                        } else {
                            onFolderListener.onSuccess(excelBean.getDatas());
                        }
                    }
                }, 200);
            }
        });
    }

    public void setOnFolderListener(OnFolderListener onFolderListener) {
        this.onFolderListener = onFolderListener;
    }

    /**
     * 代码调用开始查找文件
     *
     * @return
     */
    public void startSearch(OnFolderSearchListener onFolderSearchListener) {
        if (onFolderSearchListener != null) {
            AndroidUtils.MainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String[] columns = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MIME_TYPE,
                            MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.DATA};

                    String select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.xls'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.xlsx'" + ")";

                    ContentResolver contentResolver = context.getContentResolver();
                    Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select, null, null);

                    int columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);

                    if (cursor != null) {
                        while (cursor.moveToNext()) {

                            String path = cursor.getString(columnIndexOrThrow_DATA);

                            FileInfo document = FileUtil.getFileInfoFromFile(new File(path));

                            mList.add(document);

                        }
                        cursor.close();
                    }
                    adapter.notifyDataSetChanged();
                    onFolderSearchListener.onSearchOver();
                }
            }, 200);
        }
    }

    /**
     * 设置弹窗背景和标题文字颜色
     * @param titleBgColor
     * @param textColor
     */
    public void setTitleStyle(int titleBgColor, int textColor) {
        if (titleBgColor != -1) {
            viewBg.setBackgroundResource(titleBgColor);
        }
        if (textColor != -1) {
            titleTv.setTextColor(textColor);
        }
    }
}
