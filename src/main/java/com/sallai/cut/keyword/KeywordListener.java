package com.sallai.cut.keyword;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.sallai.cut.gui.MainGui;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 快捷键监听器
 * @author sallai
 */
@Slf4j
public class KeywordListener {
    public static void GlobalKeyWordListener() {
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
            @Override
            public void onHotKey(int key) {
                if (key == 1) {
                    showApplicationWindow();
                }
            }
        });

        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL,KeyEvent.VK_SPACE);
    }

    /**
     * 设置应用到前台，获取焦点，自动定位到鼠标位置
     */
    private static void showApplicationWindow() {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        JFrame cutHelper = MainGui.cutHelper;
        if(cutHelper.isActive()) {
            cutHelper.setVisible(false);
        }else{
            cutHelper.setLocation(mouseLocation);
            // 显示窗口
            cutHelper.setVisible(true);
            // 将窗口设置为焦点
            cutHelper.setExtendedState(JFrame.NORMAL);
            cutHelper.toFront();
            // 激活窗口
            cutHelper.requestFocus();
        }

    }
}
