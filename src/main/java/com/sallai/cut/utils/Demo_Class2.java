package com.sallai.cut.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author soil
 *
 */
public class Demo_Class2 {

    private static String sourse = "D:\\soft\\jdkAll\\miniJRE\\bin\\class.txt";
    private static String outAddress = "D:\\soft\\jdkAll\\miniJRE\\bin\\class_1.txt";

    public static void main(String[] args) {
        try {
            int count_L = 0;
            int loopCount = 0;
            // 源文件位置，打开它
            FileInputStream fin = new FileInputStream(sourse);
            InputStreamReader isr = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(isr);
            // 输出文件位置
            FileOutputStream fout = new FileOutputStream(outAddress);
            OutputStreamWriter osw = new OutputStreamWriter(fout);
            BufferedWriter bw = new BufferedWriter(osw);
            // 读一行
            String sp = br.readLine();
            // 只要没有读到文件尾就一直执行
            while (sp != null  ) {
                if (sp.length() < 1) {
                    System.out.println("发现一次！");
                }
                // 只读取以"[L"为开头的行
                else if (sp.substring(0, 2).equals("[L")) {
                    // 以空格来分隔这个行，返回的字符串数组中的第二个就是我们需要的信息
                    String str = sp.split(" ")[1];
                    str = str.replaceAll("\\.","/");
//                    StringBuilder bs = new StringBuilder(s);
//                    // 这里是测试输出，可以不加
//                    // 只是个测试输出，可以不加
//                    System.out.println(bs);
//                    // 循环遍历这个字符串，修改它，使它变成我们需要的格式
////                    for (int i = 0; i < bs.length(); i++) {
////                        char ch = bs.charAt(i);
////                        // 简化循环，因为我们得到的信息很有规律。只要出现大写的字母，就说明已经到了不需要执行的时候了。
////                        if (ch >= 65 && ch < 91)
////                            break;
////                        // 把'.'替换成'/',当然，代码中是因为方法的参数要求。
////                        if (ch == '.') {
////                            bs.replace(i, i + 1, "/");
////                        }
////                        // 这个是循环的执行此时。
////                        loopCount++;
////                    }
                    // 这里在输出你的文件信息。加工后用于后续操作。
                    bw.write(str + '\n');
                    // 程序需要的类文件数目。
                    count_L++;
                }
                // 读行
                sp = br.readLine();
            }
            // 两个测试输出
            System.out.println(count_L);
//            System.out.println(loopCount);
            // 千万要把两个文件关闭！！！
            br.close();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

