package com.sallai.cut;

import com.sallai.cut.gui.MainGui;
import com.sallai.cut.keyword.KeywordListener;
import com.sallai.cut.utils.CutList;
import com.sallai.cut.utils.CutUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.sallai.cut.utils.AppConstant.READ_CUT_FREQ_MS;

/**
 * @description: cut application$
 * @author: sallai
 * @time: 2022年2月27日 0027 上午 10:17:45 秒
 */
@Slf4j
public class CutApplication {
    public static void main(String[] args) throws IOException {
        new MainGui().initGui();
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            log.info("jvm closing");
            CutList.getInstance().saveListToFile();
            log.info("save info end");
        }));

        //org.apache.commons.lang3.concurrent.BasicThreadFactory
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
               new BasicThreadFactory.Builder().namingPattern("read-cut-thread-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{
                    CutList.getInstance().addCutCard(CutUtil.getSysClipboardText());
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },1000,READ_CUT_FREQ_MS, TimeUnit.MILLISECONDS);

        //注册热键监听事件
        KeywordListener.GlobalKeyWordListener();
        log.info("启动完毕");

    }


}
