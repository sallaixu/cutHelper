package com.sallai.cut.utils;

import com.alibaba.fastjson.JSONObject;
import com.sallai.cut.gui.MainGui;


import java.io.*;
import java.util.*;

/**
 * @description: $
 * @author: sallai
 * @time: 2022年2月27日 0027 下午 12:25:49 秒
 */

public class CutList {
    private static final CutList mInstance = new CutList();
    private static LinkedList<String> mCutlist = new LinkedList<String>();
    private static final int HISTORY_NUM = 20;
    private static int add_count = 0;
    private static String Pre_String = "";
    private CutList(){

    }

    public static CutList getInstance() {
//        log.info("get a CutList instance");
        return mInstance;
    }

    public boolean addCutCard(String str) {
        if("".equals(str)) {
//            log.debug("add Cut Card is empty!");
            return false;
        }
        if( Pre_String.equals(str) ) {
//            log.debug("current add str is same and pre_string");
            return false;
        }
        Pre_String = str;
        if( mCutlist.size() >= HISTORY_NUM ) {
            mCutlist.addFirst(str);
            mCutlist.removeLast();
        } else {
            mCutlist.addFirst(str);
        }
//        log.debug("current cutList size is ->"+mCutlist.size());

        updateGui();
        System.out.println("update gui1");
        ++add_count;
        if( add_count > 3 ) {
            saveListToFile();
            add_count = 0;
        }
        return true;

    }

    public void updateGui() {

        String[] strs = new String[mCutlist.size()];
        for (int i = 0; i < mCutlist.size(); i++) {
            strs[i] = i+1+"."+mCutlist.get(i);
        }
        MainGui.updateCutList(strs);
    }

    public LinkedList<String> getmCutlist() {
        return mCutlist;
    }


    public boolean saveListToFile() {

        File file = new File(LocalConstants.LIST_SAVE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("create LIST_SAVE_PATH fail");
                e.printStackTrace();
                return false;
            }
        }
        String jsonString = JSONObject.toJSONString(mCutlist);
        FileWriter fileWriter =null;
        try {
            fileWriter = new FileWriter(file,false);
            fileWriter.write(jsonString);
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
            try {
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }

        return true;

    }


    public boolean getListFromFile() {

        File file = new File(LocalConstants.LIST_SAVE_PATH);
        if (!file.exists()) {
            System.out.println("read file fail, file not exists");
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            FileReader fileReader = new FileReader(file);
            char[] chars = new char[100];
            int len = fileReader.read(chars);
            while(len > 0) {
                stringBuffer.append(String.valueOf(chars,0,len));
                len = fileReader.read(chars);
            }
            List<String> list = JSONObject.parseArray(stringBuffer.toString(),String.class);
            mCutlist.clear();
            mCutlist.addAll(list);
            updateGui();
            System.out.println("update gui2");
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }



}
