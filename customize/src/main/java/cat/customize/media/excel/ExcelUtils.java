package cat.customize.media.excel;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


/**
 * Created by HKR on 2017/2/22.
 */

public class ExcelUtils {

    public static WritableFont arial14font = null;

    public static WritableCellFormat arial14format = null;
    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";

    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14,
                    WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 16,
                    WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.WHITE);
            arial12font = new WritableFont(WritableFont.ARIAL, 12);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {

            e.printStackTrace();
        }
    }

    public static void initExcel(String fileName, List<ExcelTitleBean> colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("单据表", 0);
            sheet.addCell((WritableCell) new Label(0, 0, fileName,
                    arial14format));
            for (int col = 0; col < colName.size(); col++) {
                sheet.addCell(new Label(col, 0, colName.get(col).getRowName(), arial10format));
            }
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName),
                        workbook);
                WritableSheet sheet = writebook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        String cont = list.get(i);
                        sheet.addCell(new Label(i, j + 1, cont, arial12format));
                    }
                }
                writebook.write();
//                Toast.makeText(c, "导出到手机存储中文件夹Family成功", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(c);
//                builder.setTitle("导出到手机存储中文件夹Leader成功");
//                builder.setNegativeButton("确定", null);
//                builder.show();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

//    public static  List<BillObject> read2DB(File f, Context con) {
//        ArrayList<BillObject> billList = new ArrayList<BillObject>();
//        try {
//            Workbook course = null;
//            course = Workbook.getWorkbook(f);
//            Sheet sheet = course.getSheet(0);
//
//            Cell cell = null;
//            for (int i = 1; i < sheet.getRows(); i++) {
//                BillObject tc = new BillObject();
//                cell = sheet.getCell(1, i);
//                tc.setFood(cell.getContents());
//                cell = sheet.getCell(2, i);
//                tc.setClothes(cell.getContents());
//                cell = sheet.getCell(3, i);
//                tc.setHouse(cell.getContents());
//                cell = sheet.getCell(4, i);
//                tc.setVehicle(cell.getContents());
//                Log.d("gaolei", "Row"+i+"---------"+tc.getFood() + tc.getClothes()
//                        + tc.getHouse() + tc.getVehicle());
//                billList.add(tc);
//
//            }
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return billList;
//    }

    public static Object getValueByRef(Class cls, String fieldName) {
        Object value = null;
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName
                .substring(0, 1).toUpperCase());
        String getMethodName = "get" + fieldName;
        try {
            Method method = cls.getMethod(getMethodName);
            value = method.invoke(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
        String dir = sdDir.toString();
        return dir;

    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }


    public static void getString(String filePath, String str, Context c) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("导出到手机存储中文件夹Leader成功");
            builder.setNegativeButton("确定", null);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readExcel(Context activity, String url, OnExcelDataCallback onExcelDataCallback) {
        try {
            //读取assets下的文件
            File file = new File(url);
            InputStream path = new FileInputStream(file);
            Workbook book = Workbook.getWorkbook(path);         //只能读取 .xsl文件，不能读取
            book.getNumberOfSheets();
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();
            System.out.println("当前工作表的名字:" + sheet.getName());
            System.out.println("总行数:" + Rows);
            System.out.println("总列数:" + Cols);

            ExcelBean excelBean = new ExcelBean();
            excelBean.setExcelName(sheet.getName());
            List<ExcelBean.RowData> rowDataList = new ArrayList<>();
            for (int i = 0; i < Rows; i++) {
                ExcelBean.RowData rowData = new ExcelBean.RowData();
                List<String> colList = new ArrayList<>();
                for (int j = 0; j < Cols; j++) {
                    colList.add((sheet.getCell(j, i)).getContents());
                }
                rowData.setCols(colList);
                rowDataList.add(rowData);
            }
            excelBean.setDatas(rowDataList);

            onExcelDataCallback.onExcelDataResult(excelBean);
//            for (int i = 0; i < Cols; ++i) {
//                for (int j = 0; j < Rows; ++j) {
//                    System.out.print((sheet.getCell(i, j)).getContents() + "\t");
//                }
//                System.out.print("\n");
//            }
//            // 得到第一列第一行的单元格
//            Cell cell1 = sheet.getCell(0, 0);
//            String result = cell1.getContents();
//            System.out.println(result);
            book.close();
        } catch (Exception e) {
            ExcelBean excelBean = new ExcelBean();
            excelBean.setExcelName("error");
            onExcelDataCallback.onExcelDataResult(excelBean);
        }
    }

    public interface OnExcelDataCallback {
        void onExcelDataResult(ExcelBean excelBean);
    }
}
