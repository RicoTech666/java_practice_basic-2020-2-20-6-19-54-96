package com.thoughtworks.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtil {

    /**
     * 完成复制文件夹方法:
     * 1. 把给定文件夹from下的所有文件(包括子文件夹)复制到to文件夹下
     * 2. 保证to文件夹为空文件夹，如果to文件夹不存在则自动创建
     * <p>
     * 例如把a文件夹(a文件夹下有1.txt和一个空文件夹c)复制到b文件夹，复制完成以后b文件夹下也有一个1.txt和空文件夹c
     */
    public static void copyDirectory(File from, File to) {
        checkToStatus(to);
        for (File file : Objects.requireNonNull(from.listFiles())) {
            if (file.isDirectory()) {
                copyDirectory(new File(from.getAbsolutePath() + File.separator + file.getName()),
                        new File(to.getAbsolutePath() + File.separator + file.getName()));
            } else {
                copyFile(new File(from.getAbsolutePath() + File.separator + file.getName()),
                        new File(to.getAbsolutePath() + File.separator + file.getName()));
            }
        }
    }

    private static void checkToStatus(File to) {
        if (!to.exists()) {
            to.mkdir();
        } else {
            List<File> fileList = new ArrayList<>();
            getAllFiles(to, fileList);
            for (File file : fileList) {
                file.delete();
            }
        }
    }

    private static void copyFile(File from, File to) {
        try (FileInputStream fis = new FileInputStream(from);
             FileOutputStream fos = new FileOutputStream(to)) {
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void getAllFiles(File from, List<File> fileList) {
        File[] files = from.listFiles();
        assert files != null;
        for (File file : files) {
            fileList.add(file);
            if (file.isDirectory()) {
                getAllFiles(file, fileList);
            }
        }
    }

}


