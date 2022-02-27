package com.sallai.cut;

import com.sallai.cut.gui.MainGui;
import com.sallai.cut.utils.CutList;
import com.sallai.cut.utils.CutUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: cut application$
 * @author: sallai
 * @time: 2022年2月27日 0027 上午 10:17:45 秒
 */
public class CutApplication {
    public static void main(String[] args) throws IOException {
        MainGui.initGui();
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            System.out.println("jvm closing");
            CutList.getInstance().saveListToFile();
            System.out.println("save info end");
        }));
        while(true) {
            try {
                CutList.getInstance().addCutCard(CutUtil.getSysClipboardText());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("error");
                e.printStackTrace();
            }
        }

    }


}
