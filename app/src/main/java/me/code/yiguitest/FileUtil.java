package me.code.yiguitest;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


/**
 * 视频文件搜索工具类
 * Created by Administrator on 2016/12/17.
 */
public class FileUtil {
    public static ArrayList<String> doSearch(String path) {
        ArrayList<String> fileTempList = new ArrayList();
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileArray = file.listFiles();
                for (File f : fileArray) {
                    if (f.isDirectory()) {
                        doSearch(f.getPath());
                    } else {
                        if (f.getName().endsWith(".mp4") || f.getName().endsWith(".3gp")) {
                            fileTempList.add(f.getAbsolutePath());
                        }
                    }
                }
            }
        }

        return fileTempList;
    }
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
    //生成的二维码保存的根目录
    public static String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        Log.e("root",context.getFilesDir().getAbsolutePath());
        return context.getFilesDir().getAbsolutePath();
    }

}
