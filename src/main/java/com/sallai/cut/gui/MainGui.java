package com.sallai.cut.gui;

import com.sallai.cut.utils.CutList;
import com.sallai.cut.utils.CutUtil;
import com.sallai.cut.utils.NumberField;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @description: main gui$
 * @author: sallai
 * @time: 2022年2月27日 0027 上午 10:23:03 秒
 */
public class MainGui {
    public static JList mJList = null;
    public static long start = 0;
    public static int select_index = -1;
    public static void initGui() {
        JFrame cut_helper = new JFrame("cut");
        cut_helper.setSize(250, 300);// 设置窗口大小
        cut_helper.setResizable(false);
        cut_helper.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        cut_helper.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）

        //组件
//        JLabel jLabel = new JLabel("开机启动");
//        JLabel lable_card_num = new JLabel("历史记录条数");
        JLabel author_name = new JLabel("author: sallai");
//        JLabel author_email = new JLabel("email: sallai@aliyun.com");
        JCheckBox cb_top = new JCheckBox("置顶");

        cb_top.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cut_helper.setAlwaysOnTop(cb_top.isSelected());
            }
        });

        String[] str = {"hello,this is wating the data init"};
        mJList = new JList(str);
        mJList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                long end = System.currentTimeMillis();
                if( mJList.getSelectedIndex() == select_index && (end - start) < 1000) {
                    CutUtil.setSysClipboardText(CutList.getInstance().getmCutlist().get(select_index));
//                    log.info("copy success");
                }
                select_index = mJList.getSelectedIndex();
                start = end;

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        JScrollPane jScrollPane = new JScrollPane(mJList);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setSize(250,500);
//        JEditorPane jEditorPane = new JEditorPane();
//        jEditorPane.setDocument(new NumberField());
//        JCheckBox aTrue = new JCheckBox();
//        aTrue.setSelected(false);
        //采用网格布局管理器
        GridBagLayout gridBag = new GridBagLayout();    // 布局管理器
        GridBagConstraints c = null;                    // 约束
        JPanel jPanel = new JPanel(gridBag);
        GridBagConstraints gridBagConstraints=new GridBagConstraints();//实例化这个对象用来对组件进行管理


        gridBagConstraints=new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth=1;
        gridBagConstraints.gridheight=1;
        gridBag.setConstraints(cb_top, gridBagConstraints);


        gridBagConstraints=new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        gridBagConstraints.gridwidth=4;
        gridBagConstraints.gridheight=1;
        gridBag.setConstraints(jScrollPane, gridBagConstraints);


        gridBagConstraints=new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=3;
        gridBagConstraints.gridwidth=4;
        gridBagConstraints.gridheight=1;
        gridBag.setConstraints(author_name, gridBagConstraints);

        //添加面板

        jPanel.add(jScrollPane);
        jPanel.add(author_name);
        jPanel.add(cb_top);

        cut_helper.setContentPane(jPanel);
        cut_helper.setAlwaysOnTop(false);
        cut_helper.pack();
        cut_helper.setVisible(true);// 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。

        //读取记录
        CutList.getInstance().getListFromFile();
    }

    public static void updateCutList(String[] strs) {
        mJList.setListData(strs);
    }
}
