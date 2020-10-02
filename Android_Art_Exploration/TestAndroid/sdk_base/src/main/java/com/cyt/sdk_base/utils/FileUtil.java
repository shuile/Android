package com.cyt.sdk_base.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author chenyiting
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 删除目录以及目录下所有文件及文件夹
     *
     * @param fileDir 文件夹路径
     */
    public static void deleteAllFiles(String fileDir) {
        deleteAllFiles(new File(fileDir));
    }

    /**
     * 删除目录以及目录下所有文件及文件夹
     *
     * @param file 文件夹对象
     */
    public static void deleteAllFiles(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files.length > 0) {
                    for (File f : files) {
                        deleteAllFiles(f);
                    }
                } else {
                    file.delete();
                }
            }
        }
    }

    /**
     * 删除目录下指定数量的文件
     *
     * @param fileName
     * @param num
     */
    public static void deleteCertainAmountFiles(String fileName, int num) {
        deleteCertainAmountFiles(new File(fileName), num);
    }

    /**
     * 删除目录下指定数量的文件
     *
     * @param file
     */
    public static void deleteCertainAmountFiles(File file, int num) {
        // 删除当前时间以后的文件
        num -= deleteAllFilesAfterCurrentTime(file);
        if (num <= 0) {
            return;
        }
        deleteCertainAmountOldestFiles(file, num);
    }

    /**
     * 删除当前时间以后的所有文件
     *
     * @return 删除的文件数量
     */
    public static int deleteAllFilesAfterCurrentTime(File file) {
        int deleteNum = 0;
        if (!file.exists() || !file.isDirectory()) {
            return deleteNum;
        }
        File[] files = file.listFiles();
        long currentTime = System.currentTimeMillis();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteNum += deleteAllFilesAfterCurrentTime(f);
            } else if (f.isFile() && f.lastModified() > currentTime) {
                f.delete();
                deleteNum++;
            }
        }
        return deleteNum;
    }

    /**
     * 删除指定数量的老文件
     */
    public static void deleteCertainAmountOldestFiles(File file, int num) {
        if (!file.exists() || !file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles();
        File deleteFile;
        while (num > 0 && files.length > 0) {
            files = file.listFiles();
            deleteFile = files[0];
            for (int i = 1; i < files.length; i++) {
                if (deleteFile.lastModified() > files[i].lastModified()) {
                    deleteFile = files[i];
                }
            }
            if (deleteFile.exists()) {
                if (deleteFile.isDirectory()) {
                    deleteCertainAmountOldestFiles(deleteFile, deleteFile.listFiles().length);
                } else if (deleteFile.isFile()) {
                    deleteFile.delete();
                }
            }
            num--;
        }
    }

    /**
     * 读取文件内容，以字节读取
     *
     * @return
     */
    public static String readFilebyBytes(String fileDir) {
        File file = new File(fileDir);
        StringBuilder builder = new StringBuilder();
        if (file.exists() || !file.isFile()) {
            return builder.toString();
        }
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, len));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e(TAG, "readFilebyBytes:e1 " + e1.getMessage() + "  file: " + fileDir);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "readFilebyBytes:e2 " + e2.getMessage() + "  file: " + fileDir);
                }
            }
        }
        return builder.toString();
    }

    /**
     * 读取文件内容，以字符读取
     *
     * @param fileDir
     * @return
     */
    public static String readFilebyChars(String fileDir) {
        File file = new File(fileDir);
        StringBuilder builder = new StringBuilder();
        if (!file.exists() || !file.isFile()) {
            return builder.toString();
        }
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            char[] buffer = new char[512];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, len));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e(TAG, "readFilebyChars:e1 " + e1.getMessage() + "  file: " + fileDir);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "readFilebyChars: e2 " + e2.getMessage() + "  file: " + fileDir);
                }
            }
        }
        return builder.toString();
    }

    /**
     * 读取文件内容，以行读取
     */
    public static String readFileByLines(String fileDir) {
        File file = new File(fileDir);
        StringBuilder builder = new StringBuilder();
        if (!file.exists() || !file.isFile()) {
            return builder.toString();
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e(TAG, "readFileByLines:e1 " + e1.getMessage() + "  file: " + fileDir);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "readFileByLines:e2 " + e2.getMessage() + "  file: " + fileDir);
                }
            }
        }
        return builder.toString();
    }
}
