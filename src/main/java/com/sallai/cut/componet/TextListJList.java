package com.sallai.cut.componet;

import com.sallai.cut.Constant.AppConstants;
import com.sallai.cut.adapter.BaseCustomMouseListener;
import com.sallai.cut.gui.MainGui;
import com.sallai.cut.utils.CutUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static com.sallai.cut.utils.AppConstant.HELLO_ITEM_INFO;

/**
 * @ClassName TextListJList
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 2:16 PM 7/14/2024
 * @Version 1.0
 **/
@Slf4j
public class TextListJList {
    public static long start = 0;
    public static int select_index = -1;
    private JPopupMenu popupMenu = new JPopupMenu();;
    private static boolean inited = false;
    public static DefaultListModel<String> listModel = new DefaultListModel<>();
    public static DefaultListModel<String> searchListModel = new DefaultListModel<>();
    private static final JList<String> mJlist = new JList<>(listModel);
    private static boolean searchState = false;

    public TextListJList() {
        init();
    }
    public void init() {
        if(!inited) {
            setCellRenderer();
            setMouseListener();
            setKeyListener();
        }
        listModel.addElement(HELLO_ITEM_INFO);
        inited = true;
    }

    public void addToGui(JScrollPane scrollPane) {
        scrollPane.setViewportView(mJlist);
    }

    /**
     * 设置单元格渲染器
     */
    private void setCellRenderer() {
        //添加默认序号

        mJlist.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String text = "[" +(index + 1)+"]" + " " + value.toString();
                if(index % 2 == 0) {
                    renderer.setBackground(new Color(210,210,210));
                    renderer.setFocusable(true);
                }
                if (isSelected) {
                    renderer.setBackground(list.getSelectionBackground());
                }
                ((JLabel) renderer).setText(text);
                renderer.setFont(AppConstants.songFont);
                return renderer;
            }
        });
    }

    /**
     * 设置鼠标监听器
     */
    private void setMouseListener() {
        mJlist.addMouseListener(new BaseCustomMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                long end = System.currentTimeMillis();
                Point point = e.getPoint();
                if( mJlist.getSelectedIndex() == select_index && (end - start) < 1000) {
                    JLabel jLabel = new JLabel("<html><body style='width: 200px; color:green;'> ^_^ 已复制~</body></html>");
                    copyToClipboard(MainGui.cutHelper,jLabel,new Point(8,-28));
                }else {
                    //转义html标签
                    String escapedValue = StringEscapeUtils.escapeHtml4(mJlist.getSelectedValue());
                    //添加你需要显示的内容
                    JLabel jLabel = new JLabel("<html><body style='width: 200px;'><pre><code>" + escapedValue
                            + "</code></pre</body></html>");
                    jLabel.setFont(AppConstants.songFont);
                    point.translate(5,5);
                    showPopupMenu(mJlist,jLabel,point);
                }
                select_index = mJlist.getSelectedIndex();
                start = end;
            }
        });
    }

    /**
     * 复制到剪切板
     * @param component 组件
     * @param label 弹出菜单内容
     * @param point 弹出菜单位置
     */
    public void copyToClipboard(Component component,JLabel label,Point point) {

        CutUtil.setSysClipboardText(mJlist.getSelectedValue());
        log.info("copy success");
        showPopupMenu(component,label,point);
    }

    /**
     * 显示弹出菜单
     * @param component 组件
     * @param label 弹出菜单内容
     * @param point 弹出菜单位置
     */
    public void showPopupMenu(Component component,JLabel label,Point point) {
        popupMenu.removeAll();
        popupMenu.add(label);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        popupMenu.show(component, point.x, point.y);
    }

    /**
     * 设置键盘监听器
     */
    public void setKeyListener() {

        mJlist.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int selectedIndex = mJlist.getSelectedIndex();
                    if (selectedIndex != -1) {
                        JLabel jLabel = new JLabel("<html><body style='width: 200px; color:green;'>^_^ 已复制~</pre</body></html>");
                        copyToClipboard(MainGui.cutHelper,jLabel,new Point(8,-28));
                    }
                }
            }
        });
    }

    /**
     * 添加数据到列表
     * @param list
     */
    public static void addListModel(Collection<String> list) {
        list.forEach(str->listModel.add(0,str));
        //搜索状态不设置列表状态
//        mJlist.setSelectedIndex(0);
//        mJlist.ensureIndexIsVisible(0);
//        if(!searchState) {
//
//        }
    }

    /**
     * 搜索关键字
     * @param keyword 关键字
     */
    public static void filterSearchText(String keyword) {
        // 遍历数据模型，设置所有项为隐藏
        log.info("search keyword: "+keyword);
        if(StringUtils.isBlank(keyword)) {
            mJlist.setModel(listModel);
            searchState = false;
            return;
        }
        searchState = true;
        searchListModel.clear();
        for (int i = 0; i < listModel.getSize(); i++) {
            String item = listModel.getElementAt(i);
            if (StringUtils.containsIgnoreCase(item, keyword)) {
                searchListModel.addElement(item);
            }
        }
        mJlist.setModel(searchListModel);
    }

    /**
     * 清空列表数据
     */
    public static void clearListModel() {
        listModel.clear();
    }
}
