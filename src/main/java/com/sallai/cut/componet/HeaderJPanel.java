package com.sallai.cut.componet;

import com.sallai.cut.gui.MainGui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.stream.IntStream;

public class HeaderJPanel extends JPanel {
    private static JTextField searchText = new JTextField(10);;

    public HeaderJPanel() {
        super();
        BoxLayout boxLayout = new BoxLayout(this,BoxLayout.X_AXIS);
        this.setLayout(boxLayout);
        JLabel authorName = new JLabel(" 搜索：");
        JCheckBox cbTop = new JCheckBox("置顶");
        searchText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                TextListJList.filterSearchText(searchText.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                TextListJList.filterSearchText(searchText.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                TextListJList.filterSearchText(searchText.getText());
            }

        });
        cbTop.setSelected(true);
        cbTop.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                MainGui.cutHelper.setAlwaysOnTop(cbTop.isSelected());
            }
        });
        this.add(cbTop);
        this.add(authorName);
        this.add(searchText);
    }

    /**
     * 请求搜索框获取焦点
     */
    public static void requestSearchFocus() {
        searchText.requestFocus();
    }



}
