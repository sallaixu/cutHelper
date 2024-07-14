package com.sallai.cut.utils;

/**
 * @ClassName CopyClassToRT
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 11:23 AM 7/14/2024
 * @Version 1.0
 **/
/**
 * Created by MY on 2020/7/6.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author soil
 *
 */
public class CopyClassToRT {
    private String source = "C:\\Users\\MY\\Desktop\\xin\\rt\\"; // 类源目录
    private String dest = "C:\\Users\\MY\\Desktop\\xin\\tt\\"; // 类拷贝目的目录
    String[] jarArr = new String[] { "rt", "charsets" };

    /***
     *
     * @param source
     *            类源目录
     * @param dest
     *            类拷贝目的目录
     * @param jarArr
     *            需要的提取的jar文件
     */
    public CopyClassToRT(String source, String dest, String[] jarArr) {
        this.source = source;
        this.dest = dest;
        this.jarArr = jarArr;
    }

    public static void main(String[] args) {
        String[] jarArr = new String[] { "rt", "charsets" };
        CopyClassToRT obj = new CopyClassToRT("D:\\soft\\jdkAll\\miniJRE\\lib",
                "D:\\soft\\jdkAll\\miniJRE\\lib\\", jarArr);
        obj.readAndCopy("D:\\soft\\jdkAll\\miniJRE\\bin\\class_1.txt");
    }

    /***
     * @param logName
     *            提取class明细
     */
    public void readAndCopy(String logName) {
        int count = 0; // 用于记录成功拷贝的类数
        try {
            FileInputStream fi = new FileInputStream(logName);
            InputStreamReader ir = new InputStreamReader(fi);
            BufferedReader br = new BufferedReader(ir);

            String string = br.readLine();
            while (string != null) {
                if (copyClass(string)) {
                    count++;
                } else {
                    System.out.println("ERROR " + count + ": " + string);
                }
                string = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
        System.out.println("count: " + count);

    }

    /**
     * 从原jar路径提取相应的类到目标路径，如将java/lang/CharSequence类从rt目录提取到rt1目录
     *
     * @param string
     *            提取类的全路径
     * @return
     * @throws IOException
     */
    public boolean copyClass(String string) throws IOException {
        if (string.lastIndexOf("/") == -1) {
            return false;
        }
        String classDir = string.substring(0, string.lastIndexOf("/"));
        String className = string.substring(string.lastIndexOf("/") + 1, string.length()) + ".class";

        FileOutputStream fout;
        FileInputStream fin;
        boolean result = false;

        for (String jar : jarArr) {
            File srcFile = new File(source + "/" + jar + "/" + classDir + "/" + className);
            if (!srcFile.exists()) {
                continue;
            }

            byte buf[] = new byte[256];
            fin = new FileInputStream(srcFile);

            /* 目标目录不存在,创建 */
            File destDir = new File(dest + "/" + jar + "1/" + classDir);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            File destFile = new File(destDir + "/" + className);
            fout = new FileOutputStream(destFile);
            int len = 0;
            while ((len = fin.read(buf)) != -1) {
                fout.write(buf, 0, len);
            }
            fout.flush();
            result = true;
            break;
        }
        return result;
    }
}


