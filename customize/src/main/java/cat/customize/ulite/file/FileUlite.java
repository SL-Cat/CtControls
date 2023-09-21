package cat.customize.ulite.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cat.customize.ulite.DateUtils;

/**
 * Created by HSL
 * on 2023/4/19.
 */

public class FileUlite {

    private static FileUlite instance;
    private Context context;
    private boolean ownFile = false;
    private String startPath;

    private FileUlite(Context context) {
        this.context = context;
    }

    public static FileUlite getInstance(Context context) {
        if (instance == null) {
            instance = new FileUlite(context);
        }
        return instance;
    }

    /**
     * 防止某些设备无法获取到文件路径,开此方法设置文件前缀地址
     *
     * @param startPath
     */
    public void setFile(String startPath) {
        if (null != startPath) {
            ownFile = true;
            this.startPath = startPath;
        } else {
            ownFile = false;
        }
    }

    private File getFile(String path) {
        if (null != path && path.length() > 2) {
            if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        }
        if (!path.startsWith("/")) path = "/" + path;
        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (ownFile && null != startPath) {
                file = new File(startPath + path);
            } else {
                file = new File(path);
            }
        } else {
            if (ownFile && null != startPath) {
                file = new File(startPath + path);
            } else {
                file = new File(context.getExternalFilesDir(null) + path);
            }
        }

        return file;
    }

    /**
     * 删除过期的文件
     *
     * @param path 文件路径 文件夹名称路径/XXX/XXX
     * @param day
     */
    public void deleteExpireFiles(String path, int day) {
        File oldFileIv = getFile(path);
        if (!oldFileIv.exists()) return;
        if (oldFileIv.isDirectory()) {
            File[] files = oldFileIv.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            long currentTimeMillis = DateUtils.getCurrentTimeMillis();
            String nowTime = DateUtils.timeMillisToDate(currentTimeMillis);
            for (File file : files) {
                if (file.isFile()) {
                    long time = file.lastModified();
                    String oldTime = DateUtils.timeMillisToDate(time);
                    String twoDay = DateUtils.getTwoDay(nowTime.substring(0, nowTime.length() - 9), oldTime.substring(0, oldTime.length() - 9));
                    if (Integer.valueOf(twoDay) > day) {
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * 给文件名，删除该路径下所有文件和文件夹
     *
     * @param path
     */
    public void deleteAllFile( String path) {
        File tempFile = getFile(path);
        try {
            if (!tempFile.exists()) return;
            if (tempFile.isDirectory()) {
                File[] files = tempFile.listFiles();
                if (files == null || files.length == 0) {
                    tempFile.delete();
                    return;
                }
                for (File file : files) {
                    deleteAllFile(path + "/" + file.getName());
                }
            }
            tempFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存图片
     *
     * @param fileName
     * @param bitmap
     */
    public boolean saveImage(String fileName, Bitmap bitmap, String path) {
        return saveImageFile(fileName, bitmap, path, 100);
    }

    /**
     * 保存图片
     *
     * @param fileName
     * @param definition 图片清晰度
     * @param bitmap
     */
    public boolean saveImage(String fileName, Bitmap bitmap, String path, int definition) {
        return saveImageFile(fileName, bitmap, path, definition);
    }

    private boolean saveImageFile(String fileName, Bitmap bitmap, String path, int definition) {
        FileOutputStream ostream = null;
        try {
            File appDir = getFile(path);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            File currentFile = new File(appDir, fileName);
            if (currentFile.exists()) {  //如果文件已经存在就不再存储
                return true;
            }
            ostream = new FileOutputStream(currentFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, definition, ostream);
            ostream.flush();
            bitmap.recycle();
            bitmap = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ostream != null) {
                    ostream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询文件夹所有文件
     * 若是文件夹,则递归再查询文件夹下得文件
     *
     * @return
     */
    public List<String> findAllFlied(String path) {
        List<String> list = new ArrayList<>();
        File file = getFile(path);
        if (file == null) return list;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return list;
            }
            for (File childFile : files) {
                list.addAll(findAllFlied(path + "/" + childFile.getName()));
            }
        } else {
            list.add(file.getName());
        }
        return list;
    }

    /**
     * 获取文件单位显示
     *
     * @param size
     * @return
     */
    public static String formatSize(long size) {
        String suffix = "B";
        double fSize = size;
        if (fSize > 1024) {
            suffix = "KB";
            fSize /= 1024;
        }
        if (fSize > 1024) {
            suffix = "MB";
            fSize /= 1024;
        }
        if (fSize > 1024) {
            suffix = "GB";
            fSize /= 1024;
        }
        return String.format(Locale.getDefault(), "%.2f %s", fSize, suffix);
    }

    /**
     * 获取路径下所有文件大小
     * @param pathStr
     * @return
     */
    public long getFiledLength(String pathStr) {
        long length = 0;
        List<String> allFlied = findAllFlied(pathStr);
        for (String s : allFlied) {
            File file = getFile(s);
            length += file.length();
        }
        return length;
    }
}
