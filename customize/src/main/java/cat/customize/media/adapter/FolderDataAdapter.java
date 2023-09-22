package cat.customize.media.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cat.customize.R;
import cat.customize.media.model.FileInfo;
import cat.customize.media.utils.FileUtil;
import cat.customize.xlist.BaseListAdapter;


/**
 * 使用遍历文件夹的方式
 * Created by yis on 2018/4/17.
 */

public class FolderDataAdapter extends BaseListAdapter<FileInfo> {


    public FolderDataAdapter(Context context, List<FileInfo> dataList) {
        super(context, dataList);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ct_folder_data_rv_item, null);
        }
        FileInfo data = list.get(position);
        TextView contentTv = convertView.findViewById(R.id.tv_content);
        TextView sizeTv = convertView.findViewById(R.id.tv_size);
        TextView timeTv = convertView.findViewById(R.id.tv_time);

        contentTv.setText(data.getFileName());
        sizeTv.setText(FileUtil.FormetFileSize(data.getFileSize()));
        timeTv.setText(data.getTime());
        return convertView;
    }


}
