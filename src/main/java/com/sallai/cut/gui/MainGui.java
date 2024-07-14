package com.sallai.cut.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.sallai.cut.componet.HeaderJPanel;
import com.sallai.cut.componet.TextListJList;
import com.sallai.cut.utils.CutList;
import com.sallai.cut.utils.Notification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static com.sallai.cut.utils.AppConstant.HELLO_ITEM_INFO;

/**
 * @description: main gui$
 * @author: sallai
 * @time: 2022年2月27日 0027 上午 10:23:03 秒
 */
@Slf4j
public class MainGui {

    private static JScrollPane jScrollPane;
    public static JFrame cutHelper;


    public void initGui() {
        FlatLightLaf.setup();
        //舒适化窗口
        cutHelper = new JFrame("CutHelper by sallai");
        // 设置窗口大小
        cutHelper.setSize(300, 400);
        cutHelper.setResizable(true);
        //设置图标
        Image icon = Toolkit.getDefaultToolkit().getImage(Notification.class.getResource("/static/img/cut.png"));
        cutHelper.setIconImage(icon);
        // 把窗口位置设置到屏幕中心
        cutHelper.setLocationRelativeTo(null);
        // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        cutHelper.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //组件
        jScrollPane = new JScrollPane();
        TextListJList textListJList = new TextListJList();
        textListJList.addToGui(jScrollPane);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setSize(1000,1000);
        jScrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }
        });
        //添加头panel
        HeaderJPanel headerJPanel = new HeaderJPanel();
        headerJPanel.setMinimumSize(new Dimension(0,20));
        headerJPanel.setMaximumSize(new Dimension(1000,20));
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);
        jPanel.add(headerJPanel);
        jPanel.add(jScrollPane);
        cutHelper.setContentPane(jPanel);
        cutHelper.setAlwaysOnTop(true);

        //读取记录
        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        cutHelper.setVisible(true);
        CutList.getInstance().getListFromFile();
    }





}
