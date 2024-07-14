package com.sallai.cut.utils;

import com.alibaba.fastjson.JSONObject;
import com.sallai.cut.componet.TextListJList;
import com.sallai.cut.gui.MainGui;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @description: $
 * @author: sallai
 * @time: 2022年2月27日 0027 下午 12:25:49 秒
 */
@Slf4j
public class CutList {
    private static final CutList mInstance = new CutList();
    private static final int HISTORY_NUM = 500;
    private static int add_count = 0;
    //上一个字符串
    private static String Pre_String = "";
    //新增记录多少时写入文件里
    private final static int saveNum = 5;
    private CutList(){

    }

    public static CutList getInstance() {
        return mInstance;
    }

    /**
     * 向界面添加值
     * @param str
     * @return
     */
    public boolean addCutCard(String str) {
        DefaultListModel<String> listModel = TextListJList.listModel;
        if("".equals(str)) {
            return false;
        }
        if( Pre_String.equals(str) ) {
            return false;
        }

        if(str.equals(listModel.get(0))) {
            return false;
        }
        Pre_String = str;
        if( listModel.size() >= HISTORY_NUM ) {
            listModel.remove(listModel.size()-1);
        }
        TextListJList.addListModel(Collections.singletonList(str));
        log.info("update gui1");
        ++add_count;
        if( add_count > saveNum) {
            saveListToFile();
            add_count = 0;
        }
        return true;

    }


    /**
     * 将记录持久化到文件中
     */
    public void saveListToFile() {

        File file = new File(LocalConstants.LIST_SAVE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.info("create LIST_SAVE_PATH fail");
                e.printStackTrace();
                return;
            }
        }
        Enumeration<String> elements = TextListJList.listModel.elements();
        List<String> saveList = new ArrayList<>();
        while (elements.hasMoreElements()) {
            saveList.add(elements.nextElement());
        }
        String jsonString = JSONObject.toJSONString(saveList);
        FileWriter fileWriter =null;
        try {
            fileWriter = new FileWriter(file,false);
            fileWriter.write(jsonString);
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
            try {
                if(fileWriter!= null) {
                    fileWriter.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }


    public void getListFromFile() {

        File file = new File(LocalConstants.LIST_SAVE_PATH);
        if (!file.exists()) {
            log.info("read file fail, file not exists");
            return;
        }
        StringBuilder stringBuffer = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            char[] chars = new char[100];
            int len = fileReader.read(chars);
            while(len > 0) {
                stringBuffer.append(String.valueOf(chars,0,len));
                len = fileReader.read(chars);
            }
            List<String> list = JSONObject.parseArray(stringBuffer.toString(),String.class);
            if(list.isEmpty()) {
                return;
            }
            Collections.reverse(list);
            TextListJList.clearListModel();
            TextListJList.addListModel(list);
            log.debug("set cut history from file");
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
